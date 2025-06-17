package com.sooum.where_android.view.hamburger.main

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.compose.AndroidFragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentEditProfileBinding
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome
import com.sooum.where_android.view.main.LocalLoadingProvider
import com.sooum.where_android.view.widget.CustomSnackBar
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.viewmodel.hambuger.ProfileEditViewModel
import kotlinx.coroutines.launch


class EditProfileFragment : HamburgerBaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate
) {
    companion object {
        const val EMAIL = "email"
        const val NICKNAME = "nickName"
        const val PROFILE_IMAGE = "imageSrc"
    }

    private lateinit var profileEditViewModel: ProfileEditViewModel

    override fun initView() {
        val nickName: String = requireArguments().getString(NICKNAME)!!
        val email: String = requireArguments().getString(EMAIL)!!
        val imageSrc: String? = requireArguments().getString(PROFILE_IMAGE)

        with(binding) {
            editNickname.setText(nickName)
            editNickname.doAfterTextChanged {
                profileEditViewModel.updateNickName(it.toString())
            }
            editEmail.setText(email)

            imageProfile.load(imageSrc) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.image_profile_default_cover)
                error(R.drawable.image_profile_default_cover)
            }

            editNickname.filters = arrayOf<InputFilter>(LengthFilter(8))
            imageCamera.setOnClickListener {
                val dialog = ImagePickerDialogFragment.getInstance(
                    handler = object : ImagePickerDialogFragment.ImageTypeHandler {
                        override fun receiveImageType(imageType: ImageAddType) {
                            profileEditViewModel.updateImageType(imageType)
                            when (imageType) {
                                is ImageAddType.Default -> {
                                    imageProfile.setImageResource(R.drawable.image_profile_default_cover)
                                }

                                is ImageAddType.Content -> {
                                    imageProfile.load(imageType.uri) {
                                        transformations(CircleCropTransformation())
                                    }
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

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageBack.setOnClickListener {
                navHostController.navigateHome()
            }
        }
    }

    fun setViewModel(
        profileEditViewModel: ProfileEditViewModel,
        loadingScreenProvider: LoadingScreenProvider
    ) {
        this.profileEditViewModel = profileEditViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileEditViewModel.btnEnabled.collect {
                    binding.btnConfirm.isEnabled = it
                }
            }
        }
        with(binding) {
            btnConfirm.setOnClickListener {
                binding.editNickname.clearFocus()
                loadingScreenProvider.startLoading()
                profileEditViewModel.updateProfileData(
                    onSuccess = {
                        loadingScreenProvider.stopLoading {
                            CustomSnackBar.make(binding.root, "변경이 완료되었습니다.", IconType.Check).show()
                        }
                    },
                    onFail = { msg ->
                        loadingScreenProvider.stopLoading {
                            CustomSnackBar.make(binding.root, msg, IconType.Error).show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EditProfileView(
    controller: NavHostController,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel()
) {
    val loadingScreenProvider = LocalLoadingProvider.current

    BackHandler() {
        controller.navigateHome()
    }
    AndroidFragment<EditProfileFragment>(
        modifier = Modifier.fillMaxSize(),
        arguments = bundleOf(
            EditProfileFragment.EMAIL to profileEditViewModel.email,
            EditProfileFragment.NICKNAME to profileEditViewModel.nickName,
            EditProfileFragment.PROFILE_IMAGE to profileEditViewModel.imageSrc
        )
    ) { editProfileFragment ->
        editProfileFragment.setViewModel(profileEditViewModel, loadingScreenProvider)
        editProfileFragment.setNavigation(
            navHostController = controller
        )
    }
}