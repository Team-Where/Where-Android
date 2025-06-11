package com.sooum.where_android.view.auth

import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.auth.AuthViewModel
import com.sooum.where_android.viewmodel.auth.SocialLoginViewmodel

abstract class AuthBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected val authViewModel: AuthViewModel by activityViewModels()
    protected val socialLoginViewModel: SocialLoginViewmodel by activityViewModels()

    protected fun showToast(message: String) = showSimpleToast(message)

    protected val loadingAlertProvider: LoadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    protected fun getNicknameInputFilters(
        maxLength: Int = 8
    ): Array<InputFilter> {
        return arrayOf(
            InputFilter.LengthFilter(maxLength),
            object : InputFilter {
                override fun filter(
                    source: CharSequence,
                    start: Int,
                    end: Int,
                    dest: Spanned,
                    dstart: Int,
                    dend: Int
                ): CharSequence? {
                    val allowedRegex = Regex("^[가-힣a-zA-Z0-9]+$")
                    val filtered = source.filter { it.toString().matches(allowedRegex) }
                    return if (filtered.length == source.length) null else filtered
                }
            }
        )
    }

    protected lateinit var navHostController: NavHostController

    @CallSuper
    open fun setNavigation(
        navHostController: NavHostController
    ) {
        this.navHostController = navHostController
    }
}
