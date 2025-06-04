package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentEmailVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailVerificationFragment : AuthBaseFragment<FragmentEmailVerificationBinding>(
    FragmentEmailVerificationBinding::inflate
) {
    private var countDownTimer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nextBtn.isEnabled = false

            nextBtn.setOnClickListener {
                authViewModel.verifyEmailCode(
                    editTextEmail.text.toString().trim(),
                    editTextCode.text.toString().trim()
                )
            }

            imageBack.setOnClickListener {
                popBackStack()
            }

            btnEmailCode.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                if (email.isNotEmpty() && isValidEmail(email)) {
                    authViewModel.getEmailAuth(email)
                    btnEmailCode.text = "재전송"
                } else {
                    showToast("이메일을 확인해주세요")
                }
            }

            editTextEmail.doAfterTextChanged { email ->
                val input = email.toString()
                authViewModel.onEmailVerifyInputChanged(input, editTextCode.text.toString())
                updateEditTextBackground(editTextEmail, isValidEmail(input))

                textEmailWrong.visibility =
                    if (input.isNotEmpty() && !isValidEmail(input)) View.VISIBLE else View.INVISIBLE
            }

            editTextEmail.setOnFocusChangeListener { _, _ ->
                val email = editTextEmail.text.toString().trim()
                updateEditTextBackground(editTextEmail, isValidEmail(email))
            }

            editTextCode.doAfterTextChanged { code ->
                authViewModel.onEmailVerifyInputChanged(
                    editTextEmail.text.toString(),
                    code.toString()
                )
                editTextCode.setBackgroundResource(R.drawable.shape_rounded_purple_radius_12)
            }

        }

        observeEmailRequestResult()
        observeEmailVerificationResult()
        setUpNextButtonStateObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

    override fun initView() {

    }

    private fun observeEmailRequestResult() {
        lifecycleScope.launch {
            authViewModel.emailRequestState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()
                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        startTimer()
                        showToast("인증코드가 전송되었습니다.")
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("인증코드 전송에 실패하였습니다.")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeEmailVerificationResult() {
        lifecycleScope.launch {
            authViewModel.emailVerifyState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()
                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        when (result.data) {
                            "Verified" -> {
                                authViewModel.setEmail(binding.editTextEmail.text.toString().trim())
                                navigateTo(PasswordFragment())
                            }

                            "Expired" -> showToast("인증번호가 만료되었습니다.")
                            "NotVerified" -> showToast("인증번호가 일치하지 않습니다.")
                            "NotSend" -> showToast("인증요청을 먼저 해주세요.")
                            else -> showToast("알 수 없는 응답입니다: ${result.data}")
                        }
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("이메일 인증 요청에 실패했습니다.")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun startTimer(durationMillis: Long = 10 * 60 * 1000L) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.textTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.textTimer.text = "00:00"
                binding.textTimeOver.visibility = View.VISIBLE
                binding.textTimer.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_scale_700))
                binding.editTextCode.setBackgroundResource(R.drawable.shape_rounded_red_radius_12)
            }
        }.start()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setUpNextButtonStateObserver() {
        with(binding) {
            val updateNextButtonState = {
                val email = editTextEmail.text.toString().trim()
                val code = editTextCode.text.toString().trim()
                nextBtn.isEnabled = isValidEmail(email) && code.isNotEmpty()
            }

            editTextEmail.doAfterTextChanged { updateNextButtonState() }
            editTextCode.doAfterTextChanged { updateNextButtonState() }
        }
    }

    private fun updateEditTextBackground(
        editText: EditText,
        isValid: Boolean,
        validBackgroundRes: Int = R.drawable.shape_rounded_purple_radius_12,
        invalidBackgroundRes: Int = R.drawable.shape_rounded_red_radius_12
    ) {
        val resId = if (isValid) validBackgroundRes else invalidBackgroundRes
        editText.setBackgroundResource(resId)
    }
}
