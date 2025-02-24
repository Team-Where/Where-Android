package com.sooum.where_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sooum.domain.model.NewMeet
import javax.inject.Inject

class NewMeetViewModel @Inject constructor(

) : ViewModel() {

     var newMeetData by mutableStateOf(
         NewMeet("","")
     )

    fun clear() {
        newMeetData = NewMeet("","")
    }

    fun updateTitle(title:String) {
        newMeetData = newMeetData.copy(title = title)
    }
}