package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentSignUpCompleteBinding
import com.sooum.where_android.view.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpCompleteFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentSignUpCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpCompleteBinding.inflate(inflater, container, false)

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.nextBtn.setOnClickListener {
           viewModel.signUp()
        }

        observeSignUpResult()


        return binding.root
    }

    private fun observeSignUpResult() {
        lifecycleScope.launch {
            viewModel.signUpState.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        showToast("회원가입 완료!")
                        (requireActivity() as AuthActivity).nextActivity()
                    }

                    is ApiResult.Fail -> {
                        showToast("회원가입 실패했습니다: $result")
                        Log.d("SignUpCompleteFragment", result.toString())
                        binding.nextBtn.isEnabled = true
                    }
                    else -> {}
                }
            }
        }
    }
}