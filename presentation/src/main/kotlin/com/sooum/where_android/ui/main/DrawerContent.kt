package com.sooum.where_android.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.sooum.where_android.databinding.FragmentSignInBinding

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    modifier: Modifier,
) {
    //TODO Add FragmentBinding
    AndroidViewBinding(
        factory = FragmentSignInBinding::inflate,
        modifier = modifier
    ) {
        editTextEmail.setText("이런식으로 수정해요")
    }
}