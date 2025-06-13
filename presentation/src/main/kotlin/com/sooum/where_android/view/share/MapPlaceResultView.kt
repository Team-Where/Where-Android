package com.sooum.where_android.view.share

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.main.home.myMeet.MyMeetContentCard
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.viewmodel.MapResultViewModel

@Composable
fun MapPlaceResultView(
    mapResultViewModel: MapResultViewModel = hiltViewModel(),
    navigateHome: () -> Unit = {},
    navigateMeet: (id: Int) -> Unit = {},
) {
    val meetDetailList by mapResultViewModel.meetDetailList.collectAsState()
    MapPlaceResultContent(
        meetDetailList,
        navigateHome,
        navigateMeet
    )
}

@Composable()
private fun MapPlaceResultContent(
    meetDetailList: List<MeetDetail> = emptyList(),
    navigateHome: () -> Unit = {},
    navigateMeet: (id: Int) -> Unit = {},
) {
    BackHandler {
        navigateHome()
    }
    var selectedId by remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                IconButton(
                    onClick = navigateHome
                ) {
                    Icon(painter = painterResource(R.drawable.icon_back), "close map place result")
                }
            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "장소를 공유할\n모임을 선택해 주세요.",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    items(meetDetailList) { meetDetail ->
                        Box {
                            MyMeetContentCard(
                                meetDetail = meetDetail,
                                onClick = {
                                    selectedId = meetDetail.id
                                }
                            )
                            if (selectedId == meetDetail.id) {
                                Box(
                                    modifier = Modifier
                                        .size(170.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.Black.copy(alpha = 0.7f))
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_agreement_color),
                                        contentDescription = null,
                                        modifier = Modifier.align(Alignment.Center),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        PrimaryButton(
            onClick = {
                navigateMeet(selectedId)
            },
            enabled = selectedId > 0,
            title = "완료",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        )
    }
}