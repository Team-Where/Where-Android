package com.sooum.where_android.view.auth.signup

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.viewmodel.AuthViewModel

abstract class AuthBaseFragment : Fragment() {
    protected val viewModel: AuthViewModel by activityViewModels()

    protected fun showToast(message: String) = showSimpleToast(message)


    protected fun navigateTo(fragment: Fragment) {
        (activity as? AuthActivity)?.navigateToFragment(fragment)
    }

    protected fun log(message: String, tag: String = "Log") {
        Log.d(tag, message)
    }

    protected fun popBackStack() {
        parentFragmentManager.popBackStack()
    }
}
