package com.sooum.where_android.view.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.compose.AndroidFragment
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.SignInFragment

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    modifier: Modifier,
) {
    //TODO With AndroidFragment
    AndroidFragment<SignInFragment>(
        modifier = modifier
    ) {
        it.binding.btnLogin.setOnClickListener {
            closeDrawer()
        }
    }
}