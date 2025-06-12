package com.sooum.where_android.view.auth.signup

import androidx.fragment.app.activityViewModels
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentSignUpCompleteBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.auth.AuthBaseFragment
import com.sooum.where_android.view.auth.navigateHome
import com.sooum.where_android.view.auth.navigateSignIn
import com.sooum.where_android.viewmodel.auth.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpCompleteFragment : AuthBaseFragment<FragmentSignUpCompleteBinding>(
    FragmentSignUpCompleteBinding::inflate
) {
    private val signInViewModel: SignInViewModel by activityViewModels()

    override fun initView() {
        with(binding) {
            imageBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            tvTitle.text = getString(R.string.signup_complete, authViewModel.name)

            nextBtn.setOnClickListener {
                nextBtn.isEnabled = false
                loadingAlertProvider.startLoading()
                authViewModel.signUp(
                    onSuccess = {
                        loadingAlertProvider.endLoading {
                            makeLogin()
                        }
                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                        binding.nextBtn.isEnabled = true
                    }
                )
            }
        }
    }

    private fun makeLogin() {
        loadingAlertProvider.startLoading()
        signInViewModel.login(
            email = authViewModel.email,
            password = authViewModel.password,
            onSuccess = {
                loadingAlertProvider.endLoading {
                    navHostController.navigateHome()
                }
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
                navHostController.navigateSignIn(
                    applyOption = {
                        this.popUpTo<ScreenRoute.AuthRoute.SocialLogin> {
                            inclusive = false
                        }
                    }
                )
            }
        )
    }
}
