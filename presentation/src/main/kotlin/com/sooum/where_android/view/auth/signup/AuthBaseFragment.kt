package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.viewmodel.AuthViewModel

abstract class AuthBaseFragment : Fragment() {

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
}
