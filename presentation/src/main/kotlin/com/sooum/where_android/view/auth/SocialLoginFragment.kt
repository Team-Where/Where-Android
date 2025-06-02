package com.sooum.where_android.view.auth

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSocialLoginBinding
import com.sooum.where_android.view.auth.signup.AgreementFragment
import com.sooum.where_android.view.auth.signup.AuthBaseFragment
import com.sooum.where_android.view.auth.signup.SocialLoginProfileSettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SocialLoginFragment : AuthBaseFragment<FragmentSocialLoginBinding>(
    FragmentSocialLoginBinding::inflate
) {
    private val launcher = registerForActivityResult<Intent, ActivityResult>(
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotificationPermission()

        with(binding) {
            textSignIn.paintFlags = textSignIn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            textSignIn.setOnClickListener {
                (activity as AuthActivity).navigateToFragment(SignInFragment())
            }

            btnSignUp.setOnClickListener {
                (activity as AuthActivity).navigateToFragment(AgreementFragment())
            }

            imgKakao.setOnClickListener {
                kakaoLogin(requireContext())
            }

            imageNaver.setOnClickListener {
                NaverIdLoginSDK.authenticate(requireContext(), launcher)
            }
        }

        observeKakaoLoginResult()
        observeNaverLoginResult()
    }

    override fun initView() {

    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> Unit

                shouldShowRequestPermissionRationale(permission) -> {
                    showToast("알림을 위해 권한이 필요해요.")
                    notificationPermissionLauncher.launch(permission)
                }

                else -> {
                    notificationPermissionLauncher.launch(permission)
                }
            }
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val message = if (isGranted) "알림 권한이 허용되었습니다." else "알림 권한이 거부되었습니다."
        showToast(message)
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
        kakaoViewModel.kakaoLogin(accessToken, refreshToken)
    }

    private fun handleNaverToken(accessToken: String, refreshToken: String) {
        kakaoViewModel.naverLogin(accessToken, refreshToken)
    }

    private fun observeKakaoLoginResult() {
        lifecycleScope.launch {
            kakaoViewModel.kakaoSignUpState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()

                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        val data = result.data
                        if (data.signUp) {
                            val bundle = Bundle().apply { putInt("userId", data.userId) }
                            navigateTo(SocialLoginProfileSettingFragment().apply {
                                arguments = bundle
                            })
                        } else {
                            showToast("카카오 로그인 성공")
                            (activity as AuthActivity).nextActivity()
                        }
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("카카오 로그인 실패")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun observeNaverLoginResult() {
        lifecycleScope.launch {
            kakaoViewModel.naverSignUpState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> loadingAlertProvider.startLoading()

                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        val data = result.data
                        if (data.signUp) {
                            val bundle = Bundle().apply { putInt("userId", data.userId) }
                            navigateTo(SocialLoginProfileSettingFragment().apply {
                                arguments = bundle
                            })
                        } else {
                            showToast("네이버 로그인 성공")
                            (activity as AuthActivity).nextActivity()
                        }
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("네이버 로그인 실패")
                    }

                    else -> Unit
                }
            }
        }
    }
}
