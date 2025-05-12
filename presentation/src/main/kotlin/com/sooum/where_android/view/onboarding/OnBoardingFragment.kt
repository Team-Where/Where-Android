package com.sooum.where_android.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding

class OnBoardingFragment : Fragment() {
    private lateinit var binding : FragmentOnBoardingFirstBinding

    companion object {
        private const val TEXT_RES = "textRes"
        private const val IMAGE_RES = "imageRes"

        fun newInstance(textResId: Int, imageResId: Int): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            fragment.arguments = Bundle().apply {
                putInt(TEXT_RES, textResId)
                putInt(IMAGE_RES, imageResId)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingFirstBinding.inflate(inflater, container, false)

        val textRes = arguments?.getInt(TEXT_RES) ?: 0
        val imageRes = arguments?.getInt(IMAGE_RES) ?: 0

        with(binding){
            textTitle.setText(textRes)
            imageOnBoarding.setImageResource(imageRes)
        }


        return binding.root
    }
}