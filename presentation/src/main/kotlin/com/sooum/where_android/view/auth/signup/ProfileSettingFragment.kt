package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentPasswordBinding
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.view.auth.AuthActivity

class ProfileSettingFragment : Fragment() {
    private lateinit var binding : FragmentProfileSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileSettingBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(SignUpCompleteFragment())
        }

        return binding.root
    }
}