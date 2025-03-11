package com.sooum.where_android.view.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentProfileSettingBinding
import com.sooum.where_android.databinding.FragmentSignUpCompleteBinding
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.main.myMeetDetail.MyMeetDetailFragment

class SignUpCompleteFragment : Fragment() {
    private lateinit var binding : FragmentSignUpCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCompleteBinding.inflate(inflater, container, false)

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        //예시 화면 보기용 임시 코드
        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyMeetActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}