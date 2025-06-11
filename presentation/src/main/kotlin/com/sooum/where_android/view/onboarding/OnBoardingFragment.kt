package com.sooum.where_android.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sooum.where_android.databinding.ActivityOnBoardingBinding

internal class OnBoardingFragment : Fragment() {
    private var _binding: ActivityOnBoardingBinding? = null
    val binding: ActivityOnBoardingBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(container) {
                adapter = ViewPagerAdapter(requireActivity())
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        with(binding.imageBack) {
                            visibility = if (position == 0) {
                                View.INVISIBLE
                            } else {
                                View.VISIBLE
                            }
                        }
                        if (position == binding.container.adapter!!.itemCount - 1) {
                            binding.skipText.visibility = View.GONE
                            binding.nextBtn.visibility = View.VISIBLE
                        } else {
                            binding.skipText.visibility = View.VISIBLE
                            binding.nextBtn.visibility = View.INVISIBLE
                        }
                    }
                })
            }
            dotsIndicator.attachTo(container)

            imageBack.setOnClickListener {
                val prevItem = container.currentItem - 1
                if (prevItem >= 0) {
                    container.currentItem = prevItem
                }
            }
        }
    }

    fun setNavigation(
        onNavigationClick: () -> Unit
    ) {
        with(binding) {
            nextBtn.setOnClickListener {
                onNavigationClick()
            }

            skipText.setOnClickListener {
                onNavigationClick()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}