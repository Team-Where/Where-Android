package com.sooum.where_android.view.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.signup.AuthBaseFragment
import kotlinx.coroutines.launch

class SignInFragment : AuthBaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                authViewModel.login(email, password)
            }

            imageBack.setOnClickListener {
                popBackStack()
            }
        }

        observeSignInResult()
    }

    override fun initView() {

    }

    private fun observeSignInResult() {
        lifecycleScope.launch {
            authViewModel.loginState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()

                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading {
                            requireActivity().finish()
                        }
                        showToast("로그인 성공")
                        (activity as AuthActivity).nextActivity()
                    }

                    is ApiResult.Fail -> loadingAlertProvider.endLoadingWithMessage("로그인 실패")

                    else -> Unit
                }
            }
        }
    }
}
