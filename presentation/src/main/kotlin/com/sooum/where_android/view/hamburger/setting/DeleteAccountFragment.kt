package com.sooum.where_android.view.hamburger.setting

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentDeleteAccountBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment

class DeleteAccountFragment : HamburgerBaseFragment<FragmentDeleteAccountBinding>(
    FragmentDeleteAccountBinding::inflate
) {
    override fun initView() {
        highlightReasonKeyword()
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
            completeBtn.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteComplete) {
                    launchSingleTop = true
                }
            }
        }
    }
}
