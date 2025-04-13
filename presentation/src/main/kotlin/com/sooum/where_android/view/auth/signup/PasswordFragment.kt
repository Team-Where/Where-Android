package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentPasswordBinding
import com.sooum.where_android.view.auth.AuthActivity
import android.graphics.Color
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : Fragment() {
    private lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)

        setupTextWatchers()

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(ProfileSettingFragment())
        }

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        updateNextButtonState()

        return binding.root
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateNextButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.editTextPassword.addTextChangedListener(textWatcher)
        binding.editTextPasswordRecheck.addTextChangedListener(textWatcher)
    }

    private fun updateNextButtonState() {
        val password = binding.editTextPassword.text.toString().trim()
        val rePassword = binding.editTextPasswordRecheck.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()

        val isPasswordValid = isValidPassword(password)
        val isMatch = password == rePassword && password.isNotEmpty()
        val isEmailNotEmpty = email.isNotEmpty()

        updatePasswordMatchMessage(password, rePassword, isMatch)
        updatePasswordValidationMessage(password, isPasswordValid)

        val isValid = isMatch && isPasswordValid && isEmailNotEmpty
        binding.nextBtn.isEnabled = isValid
        binding.nextBtn.setBackgroundResource(
            if (isValid) R.drawable.shape_rounded_button_main_color
            else R.drawable.shape_rounded_button_gray_scale_300
        )
    }

    private fun updatePasswordMatchMessage(password: String, rePassword: String, isMatch: Boolean) {
        val isInitialState = password.isEmpty() && rePassword.isEmpty()

        binding.textPasswordRepeat.text = when {
            isInitialState -> "비밀번호를 한 번 더 입력해주세요."
            !isMatch -> "비밀번호가 일치하지 않습니다."
            else -> ""
        }

        binding.textPasswordRepeat.setTextColor(
            if (!isMatch && !isInitialState) Color.RED else Color.parseColor("#374151")
        )
    }

    private fun updatePasswordValidationMessage(password: String, isPasswordValid: Boolean) {
        val isInitialState = password.isEmpty()

        binding.textPasswordVerification.text = when {
            isInitialState -> "영문+숫자+특수문자(!,~,@) 조합 8~32자"
            !isPasswordValid -> "조건에 부합하지 않습니다."
            else -> ""
        }

        binding.textPasswordVerification.setTextColor(
            if (!isPasswordValid && !isInitialState) Color.RED else Color.parseColor("#374151")
        )
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!~@])[A-Za-z\\d!~@]{8,32}$"
        return password.matches(passwordPattern.toRegex())
    }
}