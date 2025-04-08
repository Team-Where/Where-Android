package com.sooum.where_android.view.auth

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.sooum.where_android.databinding.FragmentSocialLoginBinding
import com.sooum.where_android.view.auth.signup.AgreementFragment

class SocialLoginFragment : Fragment() {
    private lateinit var binding : FragmentSocialLoginBinding
    private val PERMISSION_REQUEST_CODE = 5000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS

            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                }

                shouldShowRequestPermissionRationale(permission) -> {
                    Toast.makeText(requireContext(), "알림을 위해 권한이 필요해요.", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}