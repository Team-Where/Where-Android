package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentAgreementBinding
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.auth.SignInFragment

class AgreementFragment : Fragment() {
    private lateinit var binding : FragmentAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgreementBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            (activity as AuthActivity).navigateToFragment(EmailVerificationFragment())
        }

        return binding.root
    }
}