package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentPasswordBinding
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingFragment : Fragment() {
    private lateinit var binding : FragmentProfileSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileSettingBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(SignUpCompleteFragment())
        }

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

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
                            }
                            else -> {}
                        }
                    }
                },
                maxImage = 1
            )
            dialog.show(parentFragmentManager, ImagePickerDialogFragment.TAG)
        }


        return binding.root
    }
}