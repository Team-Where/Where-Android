package com.sooum.where_android.view.hamburger.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentSettingBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class SettingFragment : HamburgerBaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageClose.setOnClickListener {
                navHostController.navigateHome()
            }

            passwordChangeContentArea.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.EditPassword) {
                    launchSingleTop = true
                }
            }


            logoutContentArea.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Logout) {
                    launchSingleTop = true
                }
            }

            deleteAccountContentArea.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteAccount) {
                    launchSingleTop = true
                }
            }
            privatePolicyContentArea.setOnClickListener {
                navHostController.navigate(
                    ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.WebView(
                        destUrl = "https://meteor-condor-9e6.notion.site/20f912bcf29c8020a3b6e3c468c30462"
                    )
                ) {
                    launchSingleTop = true
                }
            }

            serviceContentArea.setOnClickListener {
                navHostController.navigate(
                    ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.WebView(
                        destUrl = "https://meteor-condor-9e6.notion.site/20f912bcf29c80e69e6ed03cc42776b5?pvs=74"
                    )
                ) {
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun SettingView(
    controller: NavHostController
) {
    BackHandler {
        controller.navigateHome()
    }
    AndroidFragment<SettingFragment>(
        modifier = Modifier.fillMaxSize()
    ) { settingFragment ->
        settingFragment.setNavigation(
            navHostController = controller
        )
    }
}