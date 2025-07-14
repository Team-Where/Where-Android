package com.sooum.where_android.view.hamburger.main.inquiry

import android.net.Uri
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

    private val selectedImageUris = MutableList<Uri?>(4) { null }

    override fun initView() = with(binding) {

            imageBack.setOnClickListener {
                navHostController.navigateHome()
            }

        setupImagePickers()

        completeBtn.setOnClickListener {
            loadingAlertProvider.startLoading()
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()
            val filteredUris = selectedImageUris.filterNotNull()


            inquiryViewModel.postInquiry(
                title,
                content,
                filteredUris,
                context = requireContext(),
                onSuccess = {
                    loadingAlertProvider.endLoading()
                    showToast("문의가 등록되었습니다.")
                },
                onFail = { message ->
                    loadingAlertProvider.endLoadingWithMessage(message)
                }
            )

        }
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
                                    selectedImageUris[index] = null
                                }
                                is ImageAddType.Content -> {
                                    imageView.setImageURI(imageType.uri)
                                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                                    selectedImageUris[index] = imageType.uri
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