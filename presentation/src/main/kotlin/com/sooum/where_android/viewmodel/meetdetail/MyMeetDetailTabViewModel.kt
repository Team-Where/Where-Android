package com.sooum.where_android.viewmodel.meetdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailTabViewModel @Inject constructor(

) : ViewModel() {
    var selectedTabPosition: Int = 0

}