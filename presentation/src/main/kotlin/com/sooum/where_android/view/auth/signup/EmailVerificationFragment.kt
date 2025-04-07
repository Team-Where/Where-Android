package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sooum.where_android.databinding.FragmentEmailVerificationBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment : Fragment() {
    private lateinit var binding : FragmentEmailVerificationBinding
    private val viewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(PasswordFragment())
        }

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }


}