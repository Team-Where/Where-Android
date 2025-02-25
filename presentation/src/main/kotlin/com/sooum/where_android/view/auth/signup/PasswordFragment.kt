package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentPasswordBinding
import com.sooum.where_android.view.auth.AuthActivity

class PasswordFragment : Fragment() {
    private lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(ProfileSettingFragment())
        }

        return binding.root
    }
}