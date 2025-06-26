package com.sooum.where_android.view.hamburger.setting

import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentDeleteAccountBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome
import com.sooum.where_android.view.main.LocalLoadingProvider
import com.sooum.where_android.view.widget.CustomSnackBar
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.viewmodel.hambuger.DeleteAccountViewModel

class DeleteAccountFragment : HamburgerBaseFragment<FragmentDeleteAccountBinding>(
    FragmentDeleteAccountBinding::inflate
) {
    private lateinit var deleteAccountViewModel: DeleteAccountViewModel
    private lateinit var colorStateList: ColorStateList

    override fun initView() {
        colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.light_gray),
                ContextCompat.getColor(requireContext(), R.color.main_color)
            )
        )

        highlightReasonKeyword()
        with(binding) {
            radioGroupReason.setOnCheckedChangeListener { radioGroup, index ->
                completeBtn.isEnabled = radioGroup.checkedRadioButtonId != -1
            }
            radio1.buttonTintList = colorStateList
            radio2.buttonTintList = colorStateList
            radio3.buttonTintList = colorStateList
            radio4.buttonTintList = colorStateList
        }
    }

    private fun highlightReasonKeyword() {
        val fullText = binding.textQuestion.text.toString()
        val keyword = "이유"
        val start = fullText.indexOf(keyword)
        val end = start + keyword.length

        if (start >= 0) {
            val spannable = SpannableString(fullText)
            val color = ContextCompat.getColor(requireContext(), R.color.main_color)

            spannable.setSpan(
                ForegroundColorSpan(color),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.textQuestion.text = spannable
        }
    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageClose.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                    launchSingleTop = true
                    popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting)
                }
            }
        }
    }

    fun setViewModel(
        deleteAccountViewModel: DeleteAccountViewModel,
        loadingScreenProvider: LoadingScreenProvider
    ) {
        this.deleteAccountViewModel = deleteAccountViewModel
        with(binding) {
            completeBtn.setOnClickListener {
                loadingScreenProvider.startLoading()
                deleteAccountViewModel.deleteAccount(
                    onSuccess = {
                        loadingScreenProvider.stopLoading {
                            navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteComplete) {
                                launchSingleTop = true
                            }
                        }
                    },
                    onFail = { msg ->
                        loadingScreenProvider.stopLoading {
                            CustomSnackBar.make(
                                binding.root, msg, IconType.Error
                            ).showWithAnchor(binding.completeBtn)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DeleteAccountView(
    controller: NavHostController,
    deleteAccountViewModel: DeleteAccountViewModel = hiltViewModel()
) {
    val loadingScreenProvider = LocalLoadingProvider.current
    BackHandler() {
        controller.navigateHome()
    }
    BackHandler(loadingScreenProvider.showLoading) {
        //Block Back When Loading
    }
    AndroidFragment<DeleteAccountFragment>(
        modifier = Modifier.fillMaxSize(),
        arguments = bundleOf()
    ) { deleteAccountFragment ->
        deleteAccountFragment.setNavigation(
            navHostController = controller
        )
        deleteAccountFragment.setViewModel(deleteAccountViewModel, loadingScreenProvider)
    }
}
