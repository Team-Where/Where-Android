package com.sooum.where_android.view.auth.signup

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            viewModel.setPassword(binding.editTextPassword.text.toString().trim())
            viewModel.setEmail(binding.editTextEmail.text.toString().trim())

           navigateTo(ProfileSettingFragment())
        }

        binding.imageBack.setOnClickListener {
            popBackStack()
        }

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.isValidPassword.collectLatest { isValid ->
                if (binding.editTextPassword.text.isNullOrEmpty()) {
                    binding.textPasswordVerification.text = "영문+숫자+특수문자(!,~,@) 조합 8~32자"
                } else if (isValid == false) {
                    binding.textPasswordVerification.text = "조건에 부합하지 않습니다."
                    binding.textPasswordVerification.setTextColor(Color.RED)
                } else {
                    binding.textPasswordVerification.text = ""
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isPasswordMatch.collectLatest { isMatch ->
                if (binding.editTextPasswordRecheck.text.isNullOrEmpty()) {
                    binding.textPasswordRepeat.text = "비밀번호를 한 번 더 입력해주세요."
                } else if (isMatch == false) {
                    binding.textPasswordRepeat.text = "비밀번호가 일치하지 않습니다."
                    binding.textPasswordRepeat.setTextColor(Color.RED)
                } else {
                    binding.textPasswordRepeat.text = ""
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isNextButtonEnabled.collectLatest { isEnabled ->
                binding.nextBtn.isEnabled = isEnabled
                binding.nextBtn.setBackgroundResource(
                    if (isEnabled) R.drawable.shape_rounded_button_main_color
                    else R.drawable.shape_rounded_button_gray_scale_300
                )
            }
        }
    }


    private fun setupListeners() {
        binding.editTextPassword.addTextChangedListener { viewModel.onPasswordChanged(it.toString()) }
        binding.editTextPasswordRecheck.addTextChangedListener { viewModel.onRePasswordChanged(it.toString()) }
        binding.editTextEmail.addTextChangedListener { viewModel.onEmailChanged(it.toString()) }
    }
}