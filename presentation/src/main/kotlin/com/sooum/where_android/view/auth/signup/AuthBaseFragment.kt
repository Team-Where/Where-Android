package com.sooum.where_android.view.auth.signup

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.AuthViewModel
import com.sooum.where_android.viewmodel.KakaoViewmodel
import okhttp3.internal.ws.MessageInflater

abstract class AuthBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected val authViewModel: AuthViewModel by activityViewModels()
    protected val kakaoViewModel: KakaoViewmodel by activityViewModels()
    protected fun showToast(message: String) = showSimpleToast(message)

    protected val loadingAlertProvider: LoadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    protected fun navigateTo(fragment: Fragment) {
        (activity as? AuthActivity)?.navigateToFragment(fragment)
    }

    protected fun log(message: String, tag: String = "Log") {
        Log.d(tag, message)
    }

    protected fun popBackStack() {
        parentFragmentManager.popBackStack()
    }

    fun navigateActivity(activity: Activity) {
        val intent = Intent(requireContext(), activity::class.java)
        startActivity(intent)
    }
}
