package com.sooum.where_android.view.auth.signup

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentSignUpCompleteBinding
import com.sooum.where_android.view.auth.navigateHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpCompleteFragment : AuthBaseFragment<FragmentSignUpCompleteBinding>(
    FragmentSignUpCompleteBinding::inflate
) {
    override fun initView() {
        with(binding) {
            imageBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            tvTitle.text = getString(R.string.signup_complete, authViewModel.name)

            nextBtn.setOnClickListener {
                nextBtn.isEnabled = false
                authViewModel.signUp()
            }
        }
        observeSignUpResult()
    }

    private fun observeSignUpResult() {
        lifecycleScope.launch {
            authViewModel.signUpState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }

                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading {
                            showToast("회원가입 완료!")
                            navHostController.navigateHome()
                        }
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("회원가입 실패")
                        Log.d("SignUpCompleteFragment", result.toString())
                        binding.nextBtn.isEnabled = true
                    }

                    else -> {}
                }
            }
        }
    }
}
