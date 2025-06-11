package com.sooum.where_android.view.auth

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.graphics.Paint
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.sooum.where_android.databinding.FragmentSocialLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialLoginFragment : AuthBaseFragment<FragmentSocialLoginBinding>(
    FragmentSocialLoginBinding::inflate
) {

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d("SocialLoginFragment", NaverIdLoginSDK.getAccessToken().toString())
                Log.d("SocialLoginFragment", NaverIdLoginSDK.getRefreshToken().toString())
                handleNaverToken(
                    NaverIdLoginSDK.getAccessToken().toString(),
                    NaverIdLoginSDK.getRefreshToken().toString()
                )
            }

            RESULT_CANCELED -> {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("NaverLogin", "Error $errorCode: $errorDescription")
            }
        }
    }

    override fun initView() {

        with(binding) {
            textSignIn.paintFlags = textSignIn.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            imgKakao.setOnClickListener {
                kakaoLogin(requireContext())
            }

            imageNaver.setOnClickListener {
                NaverIdLoginSDK.authenticate(requireContext(), launcher)
            }
        }
    }

    private fun kakaoLogin(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(context) { accountToken, accountError ->
                    if (accountToken != null) {
                        handleKakaoToken(accountToken.accessToken, accountToken.refreshToken)
                    } else {
                        Log.e("SocialLoginFragment", "카카오계정 로그인 실패", accountError)
                    }
                }
            } else if (token != null) {
                handleKakaoToken(token.accessToken, token.refreshToken)
            }
        }
    }

    private fun handleKakaoToken(accessToken: String, refreshToken: String) {
        loadingAlertProvider.startLoading()
        socialLoginViewModel.kakaoLogin(
            accessToken = accessToken,
            refreshToken = refreshToken,
            onSuccess = { isSignUp, userId ->
                loadingAlertProvider.endLoading()
                if (isSignUp) {
                    navHostController.navigateSocialProfile(userId)
                } else {
                    showToast("카카오 로그인 성공")
                    navHostController.navigateHome()
                }
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
            }
        )
    }

    private fun handleNaverToken(accessToken: String, refreshToken: String) {
        loadingAlertProvider.startLoading()
        socialLoginViewModel.naverLogin(
            accessToken = accessToken,
            refreshToken = refreshToken,
            onSuccess = { isSignUp, userId ->
                loadingAlertProvider.endLoading()
                if (isSignUp) {
                    navHostController.navigateSocialProfile(userId)
                } else {
                    showToast("카카오 로그인 성공")
                    navHostController.navigateHome()
                }
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
            }
        )
    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            textSignIn.setOnClickListener {
                navHostController.navigateSignIn()
            }
            btnSignUp.setOnClickListener {
                navHostController.navigateSignUp()
            }
        }
    }
}
