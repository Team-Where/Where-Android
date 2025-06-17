package com.sooum.where_android.view.hamburger

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentHamburgerMainBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.viewmodel.DrawerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HamburgerMainFragment : HamburgerBaseFragment<FragmentHamburgerMainBinding>(
    FragmentHamburgerMainBinding::inflate
) {
    private val userViewModel: DrawerViewModel by activityViewModels()

    override fun initView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.myPage.collect { myPageInfo ->
                    myPageInfo?.let {
                        binding.textNickname.text = it.nickname + "님"

                        binding.imageProfile
                            .load(it.imageSrc) {
                                placeholder(R.drawable.image_profile_default_cover)
                                error(R.drawable.image_profile_default_cover)
                            }
                    }
                }
            }
        }
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