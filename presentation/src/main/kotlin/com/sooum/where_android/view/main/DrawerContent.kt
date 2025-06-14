package com.sooum.where_android.view.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import com.sooum.where_android.view.hamburger.HamburgerMainFragment

@Composable
fun DrawerContent(
    modifier: Modifier,
    closeDrawer: () -> Unit,
    navigate: (Any) -> Unit
) {
    AndroidFragment<HamburgerMainFragment>(
        modifier = modifier
    ) { hamburgerMainFragment ->
        hamburgerMainFragment.setCloseDrawer(closeDrawer)
        hamburgerMainFragment.setNavigation(navigate)
    }
}