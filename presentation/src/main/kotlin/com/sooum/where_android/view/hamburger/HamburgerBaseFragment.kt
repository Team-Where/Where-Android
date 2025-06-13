package com.sooum.where_android.view.hamburger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.common.BaseViewBindingFragment

abstract class HamburgerBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected fun navigateTo(fragment: Fragment) {
        (activity as? AuthActivity)?.navigateToFragment(fragment)
    }
}