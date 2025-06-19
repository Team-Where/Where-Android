package com.sooum.where_android.view.main.myMeetDetail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sooum.domain.model.ShareResult
import com.sooum.where_android.MyFirebaseMessagingService
import com.sooum.where_android.databinding.ActivityMyMeetBinding
import com.sooum.where_android.parseMapShareResult
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.widget.CustomSnackBar
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailFcmViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailPlaceViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailTabViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyMeetActivity : AppCompatActivity() {

    companion object {
        const val MEET_ID = "meetId"
    }

    private lateinit var binding: ActivityMyMeetBinding

    private val myMeetDetailTabViewModel: MyMeetDetailTabViewModel by viewModels()
    private val myMeetDetailViewModel: MyMeetDetailViewModel by viewModels()
    private val myMeetDetailPlaceWithCommentViewModel: MyMeetDetailPlaceViewModel by viewModels()
    private val myMeetDetailFcmViewModel: MyMeetDetailFcmViewModel by viewModels()

    private val fcmReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val code = intent?.getStringExtra(MyFirebaseMessagingService.FCM_EXTRA_CODE).orEmpty()
            val data = intent?.getStringExtra(MyFirebaseMessagingService.FCM_EXTRA_DATA).orEmpty()

            Log.d("MainActivity-FCM", "수신된 code: $code")
            Log.d("MainActivity-FCM", "수신된 payload: $data")

            myMeetDetailFcmViewModel.updatePlaceFromFcm(code, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMeetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(MEET_ID, 0)
        if (id <= 0) {
            showSimpleToast("잘못된 접근입니다.")
            return
        }
        myMeetDetailViewModel.loadData(id)
        myMeetDetailPlaceWithCommentViewModel.loadData(id)

        val intentFilter = IntentFilter(MyFirebaseMessagingService.FCM_DATA_RECEIVED)
        registerReceiver(fcmReceiver, intentFilter, RECEIVER_EXPORTED)

        intent?.parseMapShareResult()?.let { shareResult ->
            myMeetDetailTabViewModel.selectedTabPosition = 1
            shareResult.doAddPlace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(fcmReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("JWH", intent.toString())
        intent?.parseMapShareResult()?.doAddPlace()
    }

    private fun ShareResult.doAddPlace() {
        myMeetDetailPlaceWithCommentViewModel.addPlace(
            shareResult = this,
            onSuccess = { placeId ->
                myMeetDetailTabViewModel.updateFocusId(placeId)
                CustomSnackBar.make(binding.root, "새로운 장소를 추가했습니다.", IconType.Check).show()
            },
            onFail = { msg ->
                CustomSnackBar.make(binding.root, msg, IconType.Error).show()
            }
        )
    }
}
