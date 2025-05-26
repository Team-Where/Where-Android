package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class EmailVerificationFragment : AuthBaseFragment() {
    private lateinit var binding: FragmentEmailVerificationBinding
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            nextBtn.setOnClickListener {
                authViewModel.verifyEmailCode(
                    binding.editTextEmail.text.toString().trim(),
                    binding.editTextCode.text.toString().trim()
                )
            }
            imageBack.setOnClickListener {
                popBackStack()
            }
            btnEmailCode.setOnClickListener {
                authViewModel.getEmailAuth(binding.editTextEmail.text.toString().trim())
                startTimer()
            }

            editTextEmail.doAfterTextChanged { email ->
                val input = email.toString()
                authViewModel.onEmailVerifyInputChanged(
                    email = input,
                    code = editTextCode.text.toString()
                )

                if (input.isNotEmpty() && !isValidEmail(input)) {
                    binding.textEmailWrong.visibility = View.VISIBLE
                } else {
                    binding.textEmailWrong.visibility = View.INVISIBLE
                }
            }

            editTextCode.doAfterTextChanged { code ->
                authViewModel.onEmailVerifyInputChanged(
                    email = editTextEmail.text.toString(),
                    code = code.toString()
                )
            }
        }

        observeEmailRequestResult()
        observeEmailVerificationResult()
        setUpBtnObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

    private fun observeEmailRequestResult() {
        lifecycleScope.launch {
            authViewModel.emailRequestState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }

                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
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
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }

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

    private fun setUpBtnObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.isEmailVerifyNextEnabled.collectLatest { isEnabled ->
                    binding.nextBtn.isEnabled = isEnabled
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
            }
        }.start()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}