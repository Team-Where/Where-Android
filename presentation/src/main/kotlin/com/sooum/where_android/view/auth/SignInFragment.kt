package com.sooum.where_android.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    lateinit var binding : FragmentSignInBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

        return binding.root
    }


}