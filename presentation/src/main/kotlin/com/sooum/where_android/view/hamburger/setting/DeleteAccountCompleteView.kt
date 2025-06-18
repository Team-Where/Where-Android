package com.sooum.where_android.view.hamburger.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentDeleteAccountCompleteBinding
import com.sooum.where_android.model.ScreenRoute


@Composable
fun DeleteAccountCompleteView(
    controller: NavHostController
) {
    BackHandler() {
        //Block Back Always
    }
    AndroidViewBinding(
        factory = FragmentDeleteAccountCompleteBinding::inflate,
        modifier = Modifier.fillMaxSize()
    ) {
        completeBtn.setOnClickListener {
            controller.navigate(ScreenRoute.AuthRoute) {
                popUpTo(ScreenRoute.HomeRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}