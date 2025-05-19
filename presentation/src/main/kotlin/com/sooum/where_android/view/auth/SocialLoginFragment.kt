package com.sooum.where_android.view.auth

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
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSocialLoginBinding
import com.sooum.where_android.view.auth.signup.AgreementFragment
import com.sooum.where_android.view.auth.signup.AuthBaseFragment
import com.sooum.where_android.view.auth.signup.KakaoProfileSettingFragment
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SocialLoginFragment : AuthBaseFragment() {
    private lateinit var binding: FragmentSocialLoginBinding
    private val loadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    @Inject
    lateinit var  appManageDataStore: AppManageDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialLoginBinding.inflate(inflater, container, false)

        checkNotificationPermission()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textSignIn.paintFlags = textSignIn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            textSignIn.setOnClickListener { (activity as AuthActivity).navigateToFragment(SignInFragment()) }

            btnSignUp.setOnClickListener { (activity as AuthActivity).navigateToFragment(AgreementFragment()) }

            imgKakao.setOnClickListener {
                kakaoLogin(requireContext())
                lifecycleScope.launch {
                    viewModel.kakaoLogin(
                        appManageDataStore.getKakaoAccessToken().first() ?: "",
                        appManageDataStore.getKakaoRefreshToken().first() ?: ""
                    )
                }
            }
        }
        observeKakaoLoginResult()

    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                }

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
        if (isGranted) {
            showToast("알림 권한이 허용되었습니다.")
        } else {
            showToast("알림 권한이 거부되었습니다.")
        }
    }

    private fun kakaoLogin(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.w("SocialLoginFragment", "카카오톡 로그인 실패: ${error.message}")

                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) {
                        Log.e("SocialLoginFragment", "카카오계정 로그인 실패", error)
                    } else if (token != null) {
                        Log.i("SocialLoginFragment","$token")
                        Log.i("SocialLoginFragment", "어세스토큰 ${token.accessToken}")
                        Log.i("SocialLoginFragment", "리프레시토큰 ${token.refreshToken}")
                      lifecycleScope.launch(Dispatchers.Main) {
                          appManageDataStore.saveKakaoAccessToken(token.accessToken)
                          appManageDataStore.saveKakaoRefreshToken(token.refreshToken)
                      }
                    }
                }
            } else if (token != null) {
                Log.i("SocialLoginFragment", "카카오톡 로그인 성공 ${token.accessToken}")
                Log.i("SocialLoginFragment", "카카오톡 로그인 성공 ${token.refreshToken}")
            }
        }
    }

    private fun observeKakaoLoginResult() {
        lifecycleScope.launch {
            viewModel.kakaoSignUpState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }
                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        val data = result.data
                        if (data.signUp) {
                            val bundle = Bundle().apply {
                                putInt("userId", data.userId)
                            }
                            val fragment = KakaoProfileSettingFragment().apply {
                                arguments = bundle
                            }
                            navigateTo(fragment)
                        } else {
                            showToast("카카오 로그인 성공")
                            navigateActivity(MainActivity())
                            requireActivity().finish()
                        }
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("카카오 로그인 실패")
                    }
                    else -> {}
                }
            }
        }
    }

}