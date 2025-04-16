package com.sooum.where_android.view.main.myMeetDetail.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.myMeetDetail.modal.MapShareModalFragment
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel

open class MyMeetBaseFragment : Fragment() {

    protected val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    protected val loadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }
    /**
     * 장소 공유 시트를 노출 합니다.
     */
    protected fun openMapShareSheet() {
        MapShareModalFragment.getInstance().show(
            parentFragmentManager, MapShareModalFragment.TAG
        )
    }
}