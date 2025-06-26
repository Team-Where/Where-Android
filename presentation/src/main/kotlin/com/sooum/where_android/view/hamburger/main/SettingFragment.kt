package com.sooum.where_android.view.hamburger.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentSettingBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome
import com.sooum.where_android.view.main.LocalLoadingProvider
import com.sooum.where_android.viewmodel.hambuger.SettingViewModel
import kotlinx.coroutines.launch

    class SettingFragment : HamburgerBaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::inflate
) {
    private lateinit var settingViewModel: SettingViewModel

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

    fun setViewModel(
        viewModel: SettingViewModel,
        loadingScreenProvider: LoadingScreenProvider
    ) {
        this.settingViewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingViewModel.notificationAllowed.collect {
                    binding.alertToggle.isChecked = it
                }
            }
        }
        with(binding) {
            //버전 정보 등록
            val version = settingViewModel.version.first()
            textAppVersion.text = version.toString()

            //CheckBox와 해당영역 전체에 액션읗 할당
            listOf(
                alertToggle,
                notificationContentArea
            ).forEach {
                it.setOnClickListener {
                    loadingScreenProvider.startLoading()
                    settingViewModel.updateNotificationAllowed(
                        onSuccess = {
                            loadingScreenProvider.stopLoading()
                        },
                        onFail = { msg ->
                            showSimpleToast(msg)
                            loadingScreenProvider.stopLoading()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingView(
    controller: NavHostController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    BackHandler {
        controller.navigateHome()
    }
    val loadingScreenProvider = LocalLoadingProvider.current
    AndroidFragment<SettingFragment>(
        modifier = Modifier.fillMaxSize()
    ) { settingFragment ->
        settingFragment.setViewModel(
            viewModel = settingViewModel,
            loadingScreenProvider = loadingScreenProvider
        )
        settingFragment.setNavigation(
            navHostController = controller
        )
    }
}