package com.sooum.where_android.view.hamburger

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentHamburgerMainBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.viewmodel.main.DrawerViewModel
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
                    binding.textNickname.text = myPageInfo.nickname + "님"

                    binding.imageProfile
                        .load(myPageInfo.imageSrc) {
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.image_profile_default_cover)
                            error(R.drawable.image_profile_default_cover)
                        }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.meetCount.collect { count ->
                    binding.textMeetCount.text = count.toString()
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

    private fun makeProfileEdit(): ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit {
        return ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit(
            email = userViewModel.myPage.value.email,
            nickName = userViewModel.myPage.value.nickname,
            imageSrc = userViewModel.myPage.value.imageSrc,
        )
    }

    fun setNavigation(
        navigate: (Any) -> Unit
    ) {
        with(binding) {
            listOf(
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
            btnEditProfile.setOnClickListener {
                navigate(makeProfileEdit())
            }
        }
    }
}