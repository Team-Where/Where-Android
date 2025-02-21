package com.sooum.where_android.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentSocialLoginBinding

class SocialLoginFragment : Fragment() {
    private lateinit var binding : FragmentSocialLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSocialLoginBinding.inflate(inflater, container, false)

        binding.textSignIn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(SignInFragment())
        }

        return binding.root
    }
}