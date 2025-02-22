package com.sooum.where_android.ui.main.myMeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.viewmodel.MyMeetViewModel

@Composable
fun MyMeetView(
    myMeetViewModel: MyMeetViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navigationGuide: () -> Unit,
    modifier: Modifier
) {
    val meetDetailList by myMeetViewModel.meetDetailList.collectAsState()

    MyMeetViewContent(
        openDrawer = openDrawer,
        meetDetailList = meetDetailList,
        navigationGuide = navigationGuide,
        modifier = modifier
    )
}


@Composable
private fun MyMeetViewContent(
    openDrawer: () -> Unit,
    meetDetailList: List<MeetDetail>,
    navigationGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MyMeetHeaderView(
            openDrawer = openDrawer
        )
        MyMeetListView(
            meetDetailList,
            navigationGuide = navigationGuide
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MeetListViewContentPreview() {
    MyMeetViewContent(
        openDrawer = {},
        meetDetailList = emptyList(),
        navigationGuide = {},
        modifier = Modifier
            .safeDrawingPadding()
            .padding(12.dp)
    )
}