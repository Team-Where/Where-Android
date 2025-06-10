package com.sooum.where_android.view.hamburger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.view.common.BaseViewBindingFragment

abstract class HamburgerBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

}