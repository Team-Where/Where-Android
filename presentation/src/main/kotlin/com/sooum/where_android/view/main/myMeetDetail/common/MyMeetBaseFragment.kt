package com.sooum.where_android.view.main.myMeetDetail.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.myMeetDetail.modal.MapShareModalFragment
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailCommentViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailPlaceViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailTabViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailViewModel

abstract class MyMeetBaseFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseViewBindingFragment<VB>(bindingInflater) {

    protected val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()
    protected val myMeetDetailTabViewModel: MyMeetDetailTabViewModel by activityViewModels()
    protected val myMeetDetailPlaceViewModel: MyMeetDetailPlaceViewModel by activityViewModels()
    protected val myMeetDetailCommentViewModel: MyMeetDetailCommentViewModel by activityViewModels()

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