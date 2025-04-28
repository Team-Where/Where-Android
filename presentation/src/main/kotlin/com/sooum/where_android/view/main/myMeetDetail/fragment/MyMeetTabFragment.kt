package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sooum.where_android.databinding.FragmentMyMeetTabBinding
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.EditMyMeetDetailFragment
import kotlinx.coroutines.launch

/**
 * 네비게이션 사용을 위해 Tab 화면을 분리
 * @see[R.navigation.nav_graph]
 */
class MyMeetTabFragment : MyMeetBaseFragment() {
    private lateinit var binding: FragmentMyMeetTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetTabBinding.inflate(inflater, container, false)

        binding.imageEdit.setOnClickListener {
            val bottomSheet = EditMyMeetDetailFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFragment(MyMeetDetailFragment())
        setupTabLayoutListener()

        with(binding) {
            btnBack.setOnClickListener {
                activity?.finish()
            }
        }
        lifecycleScope.launch {
            myMeetDetailViewModel.meetDetail.collect { meetDetail ->
                binding.tvTitle.text = meetDetail?.title
                binding.imageEdit.isEnabled = meetDetail?.finished != true
            }
        }
    }

    private fun setupTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> MyMeetDetailFragment()
                    1 -> MyMeetPlaceFragment()
                    else -> return
                }
                loadFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    @SuppressLint("CommitTransaction")
    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(binding.flame.id, fragment)
            .commit()
    }

}