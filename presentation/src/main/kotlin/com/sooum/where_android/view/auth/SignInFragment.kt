package com.sooum.where_android.view.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.signup.AuthBaseFragment
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.MainActivity
import kotlinx.coroutines.launch

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
                    binding.editTextEmail.text.toString().trim(),
                    binding.editTextPassword.text.toString().trim()
                )
            }
            imageBack.setOnClickListener {
                popBackStack()
            }
            observeSignInResult()
        }
    }

    private fun observeSignInResult() {
        lifecycleScope.launch {
            viewModel.loginState.collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        loadingAlertProvider.startLoading()
                    }
                    is ApiResult.Success -> {
                        loadingAlertProvider.endLoading {
                            requireActivity().finish()
                        }
                        showToast("로그인 성공")
                        navigateActivity(MainActivity())
                    }

                    is ApiResult.Fail -> {
                        loadingAlertProvider.endLoadingWithMessage("로그인 실패")
                    }
                    else -> {}
                }
            }
        }
    }
}