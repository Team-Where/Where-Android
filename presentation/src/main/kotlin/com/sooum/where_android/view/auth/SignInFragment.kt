package com.sooum.where_android.view.auth

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.viewmodel.auth.SignInViewModel

class SignInFragment : AuthBaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {
    private val signInViewModel: SignInViewModel by activityViewModels()

    override fun initView() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                loadingAlertProvider.startLoading()
                signInViewModel.login(
                    email = email,
                    password = password,
                    onSuccess = {
                        showToast("로그인 성공")
                        navHostController.navigateHome()
                        loadingAlertProvider.endLoading()
                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                    }
                )
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
