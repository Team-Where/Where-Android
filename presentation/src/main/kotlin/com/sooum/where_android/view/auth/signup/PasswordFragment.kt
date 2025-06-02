package com.sooum.where_android.view.auth.signup

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.sooum.where_android.databinding.FragmentPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : AuthBaseFragment<FragmentPasswordBinding>(
    FragmentPasswordBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            editTextEmail.setText(authViewModel.email)

            nextBtn.isEnabled = false

            imageBack.setOnClickListener {
                popBackStack()
            }

            nextBtn.setOnClickListener {
                authViewModel.setPassword(editTextPassword.text.toString().trim())
                navigateTo(ProfileSettingFragment())
            }

            editTextPassword.addTextChangedListener {
                updatePasswordValidation()
                updateNextButtonState()
            }

            editTextPasswordRecheck.addTextChangedListener {
                updateRePasswordValidation()
                updateNextButtonState()
            }
        }
    }

    override fun initView() {

    }

    private fun updatePasswordValidation() {
        with(binding) {
            val password = editTextPassword.text.toString()
            when {
                password.isEmpty() -> {
                    textPasswordVerification.text = "영문+숫자+특수문자(!,~,@) 조합 8~32자"
                    textPasswordVerification.setTextColor(Color.BLACK)
                }

                !isValidPassword(password) -> {
                    textPasswordVerification.text = "조건에 부합하지 않습니다."
                    textPasswordVerification.setTextColor(Color.RED)
                }

                else -> {
                    textPasswordVerification.text = ""
                }
            }
        }
    }

    private fun updateRePasswordValidation() {
        with(binding) {
            val password = editTextPassword.text.toString()
            val rePassword = editTextPasswordRecheck.text.toString()
            when {
                rePassword.isEmpty() -> {
                    textPasswordRepeat.text = "비밀번호를 한 번 더 입력해주세요."
                    textPasswordRepeat.setTextColor(Color.BLACK)
                }

                password != rePassword -> {
                    textPasswordRepeat.text = "비밀번호가 일치하지 않습니다."
                    textPasswordRepeat.setTextColor(Color.RED)
                }

                else -> {
                    textPasswordRepeat.text = ""
                }
            }
        }
    }

    private fun updateNextButtonState() {
        val password = binding.editTextPassword.text.toString()
        val rePassword = binding.editTextPasswordRecheck.text.toString()
        binding.nextBtn.isEnabled = isValidPassword(password) && password == rePassword
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!~@])[A-Za-z\\d!~@]{8,32}$")
        return regex.matches(password)
    }
}

