package com.sooum.where_android.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding

class OnBoardingFirstFragment : Fragment() {
    private lateinit var binding : FragmentOnBoardingFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingFirstBinding.inflate(inflater, container, false)


        return binding.root
    }
}