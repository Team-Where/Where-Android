package com.sooum.where_android.view.main.myMeet

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
import com.sooum.domain.model.Filter
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.viewmodel.MyMeetViewModel

@Composable
fun MyMeetView(
    myMeetViewModel: MyMeetViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navigationGuide: () -> Unit,
    navigationMeetDetail: (MeetDetail) -> Unit,
    modifier: Modifier
) {
    val meetDetailList by myMeetViewModel.meetDetailList.collectAsState()
    val filter by myMeetViewModel.filter.collectAsState()
    MyMeetViewContent(
        filter = filter,
        updateFilter = myMeetViewModel::updateFilter,
        openDrawer = openDrawer,
        meetDetailList = meetDetailList,
        navigationGuide = navigationGuide,
        navigationMeetDetail = navigationMeetDetail,
        modifier = modifier
    )
}


@Composable
private fun MyMeetViewContent(
    filter: Filter,
    updateFilter: (Filter) -> Unit,
    openDrawer: () -> Unit,
    meetDetailList: List<MeetDetail>,
    navigationGuide: () -> Unit,
    navigationMeetDetail: (MeetDetail) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MyMeetHeaderView(
            openDrawer = openDrawer,
            filter = filter,
            updateFilter = updateFilter,
            showFilter = meetDetailList.isNotEmpty()
        )
        MyMeetListView(
            meetDetailList = meetDetailList,
            navigationGuide = navigationGuide,
            navigationMeetDetail = navigationMeetDetail,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MeetListViewContentPreview() {
    MyMeetViewContent(
        filter = Filter.Time,
        updateFilter = {},
        openDrawer = {},
        meetDetailList = emptyList(),
        navigationGuide = {},
        navigationMeetDetail = {},
        modifier = Modifier
            .safeDrawingPadding()
            .padding(12.dp)
    )
}