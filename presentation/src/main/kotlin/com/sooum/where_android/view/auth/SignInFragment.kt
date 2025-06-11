package com.sooum.where_android.view.auth

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.auth.signup.AuthBaseFragment
import kotlinx.coroutines.launch

class SignInFragment : AuthBaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {
    override fun initView() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                authViewModel.login(email, password)
            }
        }
        observeSignInResult()
    }

    private fun observeSignInResult() {
        lifecycleScope.launch {
            authViewModel.loginState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()

                    is ApiResult.Success -> {
                        showToast("로그인 성공")
                        navHostController.navigateHome()
                        loadingAlertProvider.endLoading()
                    }

                    is ApiResult.Fail -> loadingAlertProvider.endLoadingWithMessage("로그인 실패")

                    else -> Unit
                }
            }
        }
    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageBack.setOnClickListener {
                navHostController.popBackStack<ScreenRoute.AuthRoute.SocialLogin>(inclusive = false)
            }
        }
    }
}
