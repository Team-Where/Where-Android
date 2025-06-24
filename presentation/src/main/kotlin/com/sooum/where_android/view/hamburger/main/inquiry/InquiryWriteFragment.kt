package com.sooum.where_android.view.hamburger.main.inquiry

import android.widget.ImageView
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentInquiryBinding
import com.sooum.where_android.databinding.FragmentInquiryWriteBinding
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class InquiryWriteFragment : HamburgerBaseFragment<FragmentInquiryWriteBinding>(
    FragmentInquiryWriteBinding ::inflate
) {
    private val imageViews by lazy {
        listOf(binding.inquiryImage1, binding.inquiryImage2, binding.inquiryImage3, binding.inquiryImage4)
    }

    override fun initView() = with(binding) {

            imageBack.setOnClickListener {
                navHostController.navigateHome()
            }

        setupImagePickers()
    }

    private fun setupImagePickers() {
        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val dialog = ImagePickerDialogFragment.getInstance(
                    handler = object : ImagePickerDialogFragment.ImageTypeHandler {
                        override fun receiveImageType(imageType: ImageAddType) {
                            when (imageType) {
                                is ImageAddType.Default -> {
                                    imageView.setImageResource(R.drawable.image_profile_default_cover)
                                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                                }
                                is ImageAddType.Content -> {
                                    imageView.setImageURI(imageType.uri)
                                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
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

}