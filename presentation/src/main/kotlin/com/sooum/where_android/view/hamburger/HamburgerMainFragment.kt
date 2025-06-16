package com.sooum.where_android.view.hamburger

import com.sooum.where_android.databinding.FragmentHamburgerMainBinding
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HamburgerMainFragment : HamburgerBaseFragment<FragmentHamburgerMainBinding>(
    FragmentHamburgerMainBinding::inflate
) {
    override fun initView() {

    }

    fun setCloseDrawer(
        action: () -> Unit
    ) {
        with(binding) {
            imageClose.setOnClickListener {
                action()
            }
        }
    }

    fun setNavigation(
        navigate: (Any) -> Unit
    ) {
        with(binding) {
            listOf(
                btnEditProfile to ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit,
                navigationRingArea to ScreenRoute.HomeRoute.HamburgerRoute.Notification,
                navigationFaqArea to ScreenRoute.HomeRoute.HamburgerRoute.FAQ,
                navigationPersonArea to ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute,
                navigationNoticeArea to ScreenRoute.HomeRoute.HamburgerRoute.Notice,
                navigationSettingArea to ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute
            ).forEach { (view, route) ->
                view.setOnClickListener {
                    navigate(route)
                }
            }
        }
    }
}