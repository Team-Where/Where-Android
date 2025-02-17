package com.sooum.where_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentOnBoardingSecondBinding

class OnBoardingSecondFragment : Fragment() {
    private lateinit var binding : FragmentOnBoardingSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingSecondBinding.inflate(inflater, container, false)


        return binding.root
    }
}