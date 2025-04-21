package com.sooum.where_android.view.auth

import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.sooum.where_android.databinding.FragmentSocialLoginBinding
import com.sooum.where_android.view.auth.signup.AgreementFragment
import com.sooum.where_android.view.auth.signup.AuthBaseFragment

class SocialLoginFragment : AuthBaseFragment() {
    private lateinit var binding: FragmentSocialLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialLoginBinding.inflate(inflater, container, false)

        binding.textSignIn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(SignInFragment())
        }

        binding.btnSignUp.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(AgreementFragment())
        }

        checkNotificationPermission()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textSignIn.paintFlags = textSignIn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
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
}