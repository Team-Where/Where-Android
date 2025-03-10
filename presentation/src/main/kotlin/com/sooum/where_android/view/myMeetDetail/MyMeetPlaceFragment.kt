package com.sooum.where_android.view.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
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

        binding.icWarning.setOnClickListener { view ->
            TooltipCompat.setTooltipText(view, "친구들과 가기로 결정한 장소 목록입니다")
            view.performLongClick() // 클릭 시 툴팁 표시
        }

        return binding.root
    }
}