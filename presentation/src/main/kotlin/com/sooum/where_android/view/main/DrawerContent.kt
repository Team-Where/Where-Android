package com.sooum.where_android.view.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import com.sooum.where_android.view.hamburger.setting.DeleteAccountFragment

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    modifier: Modifier,
) {
    //TODO With AndroidFragment
    AndroidFragment<DeleteAccountFragment>(
        modifier = modifier
    ) {
//        it.binding.btnLogin.setOnClickListener {
//            closeDrawer()
//        }
    }
}