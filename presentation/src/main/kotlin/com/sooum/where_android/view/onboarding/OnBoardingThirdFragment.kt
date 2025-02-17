package com.sooum.where_android.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentOnBoardingThirdBinding

class OnBoardingThirdFragment: Fragment() {
    private lateinit var binding : FragmentOnBoardingThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingThirdBinding.inflate(inflater, container, false)


        return binding.root
    }
}