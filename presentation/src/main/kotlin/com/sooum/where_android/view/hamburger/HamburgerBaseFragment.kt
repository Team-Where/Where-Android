package com.sooum.where_android.view.hamburger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.navigation.NavHostController
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.view.common.BaseViewBindingFragment

abstract class HamburgerBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected lateinit var navHostController: NavHostController

    @CallSuper
    open fun setNavigation(
        navHostController: NavHostController
    ) {
        this.navHostController = navHostController
    }

}