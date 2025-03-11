package com.sooum.where_android.view.main.myMeetDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.main.myMeetDetail.modal.MapShareModal
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun MyMeetDetailScreenView(
    myMeetDetailViewModel: MyMeetDetailViewModel = hiltViewModel(),
    id: Long,
    onBack: () -> Unit,
    navigationSchedule: () -> Unit,
    navigationInvite :() -> Unit
) {
    LaunchedEffect(true) {
        myMeetDetailViewModel.loadData(id)
    }

    val meetDetail by myMeetDetailViewModel.meetDetail.collectAsState()
    val pages = listOf("모임 정보", "장소")
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(
        pageCount = {
            pages.size
        }
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
            Text(
                text = meetDetail?.title ?: "",
                modifier = Modifier.align(Alignment.Center),
                fontFamily = pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Filled.MoreVert, null)
            }
        }
        TabRow(
            selectedTabIndex = pageState.currentPage,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pageState.currentPage]),
                    color = Gray800
                )
            }
        ) {
            pages.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = pageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pageState.scrollToPage(index)
                        }
                    },
                    text = {
                        Text(tabTitle)
                    }
                )
            }
        }
        var showMap by remember {
            mutableStateOf(false)
        }
        HorizontalPager(
            state = pageState,
        ) { page ->
            if (page == 0) {
                val state = rememberFragmentState()
                LaunchedEffect(meetDetail) {

                }
                AndroidFragment<MyMeetDetailFragment>(
                    modifier = Modifier.fillMaxSize(),
                    fragmentState = state,
                    onUpdate = { fragment ->
                        fragment.setData(meetDetail)
                        fragment.showMap {
                            showMap = true
                        }
                        fragment.navigationSchedule(navigationSchedule)
                    }
                )
            } else if (page == 1) {
                AndroidFragment<MyMeetPlaceFragment>(
                    modifier = Modifier.fillMaxSize(),
                    onUpdate = {

                    }
                )
            }
        }
        if (showMap) {
            MapShareModal(
                onDismiss = {
                    showMap = false
                }
            )
        }
    }
}