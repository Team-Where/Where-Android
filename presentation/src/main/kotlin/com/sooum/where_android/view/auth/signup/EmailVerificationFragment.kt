package com.sooum.where_android.view.auth.signup

import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentEmailVerificationBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.auth.AuthBaseFragment
import com.sooum.where_android.view.auth.navigatePassword
import com.sooum.where_android.view.widget.CustomSnackBar
import com.sooum.where_android.view.widget.IconType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment : AuthBaseFragment<FragmentEmailVerificationBinding>(
    FragmentEmailVerificationBinding::inflate
) {
    private var countDownTimer: CountDownTimer? = null

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

    override fun initView() {
        setUpNextButtonStateObserver()

        with(binding) {
            nextBtn.isEnabled = false

            nextBtn.setOnClickListener {
                loadingAlertProvider.startLoading()
                authViewModel.verifyEmailCode(
                    email = editTextEmail.text.toString().trim(),
                    code = editTextCode.text.toString().trim(),
                    onSuccess = { status ->
                        when (status) {
                            VERIFIED -> {
                                authViewModel.setEmail(binding.editTextEmail.text.toString().trim())
                                navHostController.navigatePassword()
                            }

                            else -> {
                                showVerificationResultSnackBar(status)
                            }
                        }

                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                    }
                )
            }

            imageBack.setOnClickListener {
                navHostController.popBackStack<ScreenRoute.AuthRoute.SingUpRoute.Agreement>(
                    inclusive = false
                )
            }

            btnEmailCode.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                if (email.isNotEmpty() && isValidEmail(email)) {
                    loadingAlertProvider.startLoading()
                    authViewModel.checkEmailAndAuth(
                        email = email,
                        onSuccess = {
                            loadingAlertProvider.endLoading()
                            emailCodeRequestSuccess()
                            btnEmailCode.text = "재전송"
                            editTextEmail.isEnabled = false
                            editTextCode.requestFocus()
                        },
                        onFail = { msg ->
                            loadingAlertProvider.endLoadingWithMessage(msg)
                            editTextEmail.isEnabled = true
                            editTextEmail.requestFocus()
                        }
                    )
                } else {
                    showToast("이메일을 확인해주세요")
                }
            }

            editTextEmail.doAfterTextChanged {
                val isValid = isValidEmail(it.toString())
                updateEditTextBackground(editTextEmail, isValid)
                textEmailWrong.visibility =
                    if (it.toString().isNotEmpty() && !isValid) View.VISIBLE else View.INVISIBLE
                btnEmailCode.setBackgroundResource(
                    if (isValid) R.drawable.shape_rounded_black else R.drawable.shape_rounded_gray_500
                )
            }


            editTextEmail.setOnFocusChangeListener { _, _ ->
                val email = editTextEmail.text.toString().trim()
                updateEditTextBackground(editTextEmail, isValidEmail(email))
            }

            editTextCode.doAfterTextChanged { code ->
                authViewModel.onEmailVerifyInputChanged(
                    emailValue = editTextEmail.text.toString(),
                    codeValue = code.toString()
                )
                editTextCode.setBackgroundResource(R.drawable.shape_rounded_purple_radius_12)
            }

        }
    }

    /**
     * 이메일 코드 전송 성공시, 카운트 다운을 시작하고, 스낵바를 출력한다.
     */
    private fun emailCodeRequestSuccess() {
        startTimer()
        val snackBar =
            CustomSnackBar.make(requireView(), "인증 코드가 전송되었습니다.", IconType.Check)
        snackBar.showWithAnchor(binding.nextBtn)
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
                binding.textTimer.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_scale_700
                    )
                )
                binding.editTextCode.setBackgroundResource(R.drawable.shape_rounded_red_radius_12)
                binding.btnEmailCode.isEnabled = true
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

    private fun showVerificationResultSnackBar(result: String) {
        val (message, iconType) = when (result) {
            VERIFIED -> return
            EXPIRED -> "인증번호가 만료되었습니다." to IconType.Error
            NOT_VERIFIED -> "인증번호가 일치하지 않습니다." to IconType.Error
            NOT_SEND -> "인증요청을 먼저 해주세요." to IconType.Error
            else -> "알 수 없는 응답입니다" to IconType.Error
        }
        val snackBar = CustomSnackBar.make(requireView(), message, iconType)
        snackBar.showWithAnchor(binding.nextBtn)
    }

    companion object {
        private const val VERIFIED = "Verified"
        private const val EXPIRED = "Expired"
        private const val NOT_VERIFIED = "NotVerified"
        private const val NOT_SEND = "NotSend"
    }
}
