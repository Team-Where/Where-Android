package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileSettingFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentProfileSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileSettingBinding.inflate(inflater, container, false)

        setupListeners()
        setUpObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            nextBtn.setOnClickListener{
                viewModel.setName(binding.editNickname.text.toString())
               navigateTo(SignUpCompleteFragment())
            }

            imageBack.setOnClickListener { popBackStack()}
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
                                viewModel.setProfileImage(imageType.uri.toString())
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

    private fun setUpObservers(){
        lifecycleScope.launch {
            viewModel.isNextButtonEnabled.collectLatest { isEnabled ->
                binding.nextBtn.isEnabled = isEnabled
                binding.nextBtn.setBackgroundResource(
                    if (isEnabled) R.drawable.shape_rounded_button_main_color
                    else R.drawable.shape_rounded_button_gray_scale_300
                )
            }
        }
    }

    private fun setupListeners() {
        binding.editNickname.addTextChangedListener { viewModel.onNameChanged(it.toString()) }
    }
}