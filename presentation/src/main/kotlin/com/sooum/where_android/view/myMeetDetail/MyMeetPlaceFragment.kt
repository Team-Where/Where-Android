package com.sooum.where_android.view.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding

class MyMeetPlaceFragment: Fragment() {
    private lateinit var binding : FragmentMyMeetPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetPlaceBinding.inflate(inflater, container, false)


        return binding.root
    }
}