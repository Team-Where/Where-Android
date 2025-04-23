package com.sooum.where_android.view.main.myMeetDetail

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sooum.domain.model.ShareResult
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ActivityMyMeetBinding
import com.sooum.where_android.databinding.FragmentMyMeetTabBinding
import com.sooum.where_android.view.MapShareResultActivity
import com.sooum.where_android.view.checkInviteData
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.EditMyMeetDetailFragment
import com.sooum.where_android.view.widget.CustomSnackBar
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MyMeetActivity : AppCompatActivity() {

    companion object {
        const val MEET_ID = "meetId"
    }

    private lateinit var binding: ActivityMyMeetBinding

    private val myMeetDetailViewModel: MyMeetDetailViewModel by viewModels()

    private val fcmReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val code = intent?.getStringExtra("code").orEmpty()
            val data = intent?.getStringExtra("data").orEmpty()

            Log.d("MainActivity-FCM", "수신된 code: $code")
            Log.d("MainActivity-FCM", "수신된 payload: $data")

            myMeetDetailViewModel.updatePlaceFromFcm(code,data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMeetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.checkInviteData(this@MyMeetActivity)

        intent.getIntExtra(MEET_ID, 0).let { id ->
            myMeetDetailViewModel.loadData(id)
        }

        val intentFilter = IntentFilter("FCM_DATA_RECEIVED")
        registerReceiver(fcmReceiver, intentFilter,RECEIVER_EXPORTED)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(fcmReceiver)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("JWH", intent.toString())
        intent?.checkInviteData(this@MyMeetActivity)

        intent?.extras?.getString(MapShareResultActivity.SHARE_RESULT)?.let { data ->
            val shareResult = Json.decodeFromString<ShareResult>(data)
            myMeetDetailViewModel.addPlace(shareResult) {
                CustomSnackBar.make(binding.root, "새로운 장소를 추가했습니다.", IconType.Check).show()
            }
        }
    }
}

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
