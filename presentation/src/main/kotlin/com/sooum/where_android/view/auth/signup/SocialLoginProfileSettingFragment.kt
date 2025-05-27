package com.sooum.where_android.view.auth.signup

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentSocialLoginProfileSettingBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import kotlinx.coroutines.launch

class SocialLoginProfileSettingFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentSocialLoginProfileSettingBinding
    private lateinit var selectedImageUri: Uri
    private val userId: Int by lazy {
        requireArguments().getInt("userId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSocialLoginProfileSettingBinding.inflate(inflater, container, false)

        binding.imageCamera.setOnClickListener {
            val dialog = ImagePickerDialogFragment.getInstance(
                handler = object : ImagePickerDialogFragment.ImageTypeHandler {
                    override fun receiveImageType(imageType: ImageAddType) {
                        when (imageType) {
                            is ImageAddType.Default -> {
                                // 기본 이미지 적용
                                binding.imageProfile.setImageResource(R.drawable.image_profile_default_cover)
                            }
                            is ImageAddType.Content -> {
                                // 앨범에서 선택한 이미지 적용
                                binding.imageProfile.setImageURI(imageType.uri)
                                binding.imageProfile.scaleType = ImageView.ScaleType.CENTER_CROP
                                selectedImageUri = imageType.uri
                            }
                            else -> {}
                        }
                    }
                },
                maxImage = 1
            )
            dialog.show(parentFragmentManager, ImagePickerDialogFragment.TAG)
        }

        binding.btnComplete.setOnClickListener {
            kakaoViewModel.putNickName(userId, binding.editNickname.text.toString())
            kakaoViewModel.updateProfile(userId, selectedImageUri)
        }

        observePutNicknameResult()

        return binding.root
    }

    private fun observePutNicknameResult() {
        lifecycleScope.launch {
            kakaoViewModel.updateNicknameState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }
                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading()
                        showToast("프로필 업데이트 성공.")
                        (activity as AuthActivity).nextActivity()
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("프로필 업데이트 실패")
                    }
                    else -> {}
                }
            }
        }
    }
}