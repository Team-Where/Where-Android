package com.sooum.where_android.view.auth.signup

import android.net.Uri
import android.widget.ImageView
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentSocialLoginProfileSettingBinding
import com.sooum.where_android.view.auth.AuthBaseFragment
import com.sooum.where_android.view.auth.navigateHome
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment

class SocialLoginProfileSettingFragment :
    AuthBaseFragment<FragmentSocialLoginProfileSettingBinding>(
        FragmentSocialLoginProfileSettingBinding::inflate
    ) {
    private var selectedImageUri: Uri? = null

    private val userId: Int by lazy {
        requireArguments().getInt("userId")
    }

    override fun initView() {
        with(binding) {
            editNickname.filters = getNicknameInputFilters()

            imageCamera.setOnClickListener {
                val dialog = ImagePickerDialogFragment.getInstance(
                    handler = object : ImagePickerDialogFragment.ImageTypeHandler {
                        override fun receiveImageType(imageType: ImageAddType) {
                            when (imageType) {
                                is ImageAddType.Default -> {
                                    imageProfile.setImageResource(R.drawable.image_profile_default_cover)
                                    selectedImageUri = null
                                }

                                is ImageAddType.Content -> {
                                    imageProfile.setImageURI(imageType.uri)
                                    imageProfile.scaleType = ImageView.ScaleType.CENTER_CROP
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

            btnComplete.setOnClickListener {
                loadingAlertProvider.startLoading()

                socialLoginViewModel.putNickNameAndProfile(
                    userId = userId,
                    nickName = editNickname.text.toString(),
                    imageFile = selectedImageUri,
                    onSuccess = {
                        loadingAlertProvider.endLoading()
                        navHostController.navigateHome()
                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                    }
                )
            }
        }
    }
}
