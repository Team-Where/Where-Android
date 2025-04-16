package com.sooum.where_android.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.signup.AuthBaseFragment

class SignInFragment : AuthBaseFragment() {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.login(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
            imageBack.setOnClickListener {
                popBackStack()
            }
        }
    }
}