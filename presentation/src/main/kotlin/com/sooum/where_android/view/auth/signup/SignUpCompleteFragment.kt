package com.sooum.where_android.view.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.databinding.FragmentSignUpCompleteBinding
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.main.myMeetDetail.MyMeetDetailFragment
import com.sooum.where_android.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpCompleteFragment : Fragment() {
    private lateinit var binding : FragmentSignUpCompleteBinding
    private val viewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCompleteBinding.inflate(inflater, container, false)

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.nextBtn.setOnClickListener {
            Log.d("SignUpCompleteFragment", "email: ${viewModel.email}, password: ${viewModel.password}, name: ${viewModel.name}")
           viewModel.signUp()
        }


        return binding.root
    }
}