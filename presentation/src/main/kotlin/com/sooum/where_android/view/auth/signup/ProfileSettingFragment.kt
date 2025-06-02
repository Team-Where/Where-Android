package com.sooum.where_android.view.auth.signup

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileSettingFragment : AuthBaseFragment<FragmentProfileSettingBinding>(
    FragmentProfileSettingBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nextBtn.isEnabled = !editNickname.text.isNullOrBlank()

            nextBtn.setOnClickListener {
                authViewModel.setName(editNickname.text.toString().trim())
                navigateTo(SignUpCompleteFragment())
            }

            editNickname.addTextChangedListener { editable ->
                nextBtn.isEnabled = !editable.isNullOrBlank()
            }

            imageBack.setOnClickListener { popBackStack() }

            imageCamera.setOnClickListener {
                val dialog = ImagePickerDialogFragment.getInstance(
                    handler = object : ImagePickerDialogFragment.ImageTypeHandler {
                        override fun receiveImageType(imageType: ImageAddType) {
                            when (imageType) {
                                is ImageAddType.Default -> {
                                    imageProfile.setImageResource(R.drawable.image_profile_default_cover)
                                    val defaultImageUri = Uri.parse("android.resource://${requireContext().packageName}/${R.drawable.image_profile_default_cover}")
                                    authViewModel.setProfileImage(defaultImageUri.toString())
                                }

                                is ImageAddType.Content -> {
                                    imageProfile.setImageURI(imageType.uri)
                                    imageProfile.scaleType = ImageView.ScaleType.CENTER_CROP
                                    authViewModel.setProfileImage(imageType.uri.toString())
                                }

                                else -> {}
                            }
                        }
                    },
                    maxImage = 1
                )
                dialog.show(parentFragmentManager, ImagePickerDialogFragment.TAG)
            }
        }
    }

    override fun initView() {

    }
}
