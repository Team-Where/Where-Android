package com.sooum.where_android.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentOnBoardingContentBinding

class OnBoardingContentFragment : Fragment() {
    private var _binding: FragmentOnBoardingContentBinding? = null
    val binding: FragmentOnBoardingContentBinding
        get() = _binding!!

    companion object {
        private const val TEXT_RES = "textRes"
        private const val IMAGE_RES = "imageRes"

        private fun newInstance(
            @StringRes textResId: Int,
            @DrawableRes imageResId: Int
        ): OnBoardingContentFragment {
            val fragment = OnBoardingContentFragment()
            fragment.arguments = Bundle().apply {
                putInt(TEXT_RES, textResId)
                putInt(IMAGE_RES, imageResId)
            }
            return fragment
        }

        fun step1Instance() = newInstance(R.string.onboarding_logo1, R.drawable.image_splash_1)
        fun step2Instance() = newInstance(R.string.onboarding_logo2, R.drawable.image_splash_2)
        fun step3Instance() = newInstance(R.string.onboarding_logo3, R.drawable.image_splash_3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingContentBinding.inflate(inflater, container, false)

        val textRes = arguments?.getInt(TEXT_RES) ?: 0
        val imageRes = arguments?.getInt(IMAGE_RES) ?: 0

        with(binding) {
            textTitle.setText(textRes)
            imageOnBoarding.setImageResource(imageRes)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}