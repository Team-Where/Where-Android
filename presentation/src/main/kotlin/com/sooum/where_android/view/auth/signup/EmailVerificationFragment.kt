package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentEmailVerificationBinding
import com.sooum.where_android.view.auth.AuthActivity

class EmailVerificationFragment : Fragment() {
    private lateinit var binding : FragmentEmailVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(PasswordFragment())
        }

        return binding.root
    }
}