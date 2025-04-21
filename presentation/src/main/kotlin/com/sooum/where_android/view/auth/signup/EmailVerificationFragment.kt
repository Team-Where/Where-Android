package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sooum.where_android.databinding.FragmentEmailVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentEmailVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
           navigateTo(PasswordFragment())
        }

        binding.imageBack.setOnClickListener {
           popBackStack()
        }

        return binding.root
    }

}