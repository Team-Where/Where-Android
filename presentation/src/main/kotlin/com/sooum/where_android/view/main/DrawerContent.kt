package com.sooum.where_android.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import com.sooum.where_android.view.hamburger.setting.DeleteAccountFragment
import com.sooum.where_android.model.ScreenRoute

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    modifier: Modifier,
    navigate: (Any) -> Unit
) {
    //TODO With AndroidFragment
//    AndroidFragment<DeleteAccountFragment>(
//        modifier = modifier
//    ) {
//    AndroidFragment<SignInFragment>(
//        modifier = modifier
//    ) {
//        it.binding.btnLogin.setOnClickListener {
//            closeDrawer()
//        }
//    }

    Column(
        modifier = modifier
    ) {
        Button(
            onClick = {
                closeDrawer()
            }
        ) {
            Text("닫기")
        }

        listOf(
            ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit,
            ScreenRoute.HomeRoute.HamburgerRoute.Notification,
            ScreenRoute.HomeRoute.HamburgerRoute.FAQ,
            ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute,
            ScreenRoute.HomeRoute.HamburgerRoute.Notice,
            ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute,
        ).forEach {
            TextButton(
                onClick = {
                    navigate(it)
                }
            ) {
                Text(it.toString())
            }
        }
    }
}