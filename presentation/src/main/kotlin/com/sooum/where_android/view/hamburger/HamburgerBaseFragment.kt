package com.sooum.where_android.view.hamburger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.viewmodel.hambuger.InquiryTabViewModel
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.setting.InquiryViewModel
import com.sooum.where_android.viewmodel.setting.NoticeViewModel
import com.sooum.where_android.viewmodel.setting.NotificationViewModel

abstract class HamburgerBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected lateinit var navHostController: NavHostController

    protected val inquiryTabViewModel: InquiryTabViewModel by activityViewModels()

    protected val inquiryViewModel: InquiryViewModel by activityViewModels()

    protected val noticeViewModel: NoticeViewModel by activityViewModels()

    protected val notificationViewModel: NotificationViewModel by activityViewModels()

    protected val loadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    protected fun showToast(message: String) = showSimpleToast(message)

    @CallSuper
    open fun setNavigation(
        navHostController: NavHostController
    ) {
        this.navHostController = navHostController
    }

}