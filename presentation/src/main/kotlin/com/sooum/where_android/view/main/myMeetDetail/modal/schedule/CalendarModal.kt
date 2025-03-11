package com.sooum.where_android.view.main.myMeetDetail.modal.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray200
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.ModalHeader
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.util.DateUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarModal(
    startData: LocalDate? = null,
    onDismiss: () -> Unit,
    onClick: (LocalDate) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    var selected: LocalDate? by remember {
        mutableStateOf(
            startData
        )
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState,
    ) {
        Column {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            ModalHeader(
                Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .padding(start = 20.dp, end = 8.dp),
                header = "날짜 선택",
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                },
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            HorizontalDivider()
            CalendarView(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .navigationBarsPadding(),
                selected = selected,
                onClick = {
                    selected = it
                },
                start = selected ?: Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date,
                onNext = { date ->
                    scope.launch {
                        async {
                            sheetState.hide()
                            onDismiss()
                        }
                        async {
                            onClick(date)
                        }
                    }
                }
            )
        }
    }
}


@Composable
private fun CalendarView(
    modifier: Modifier = Modifier,
    start: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date,
    selected: LocalDate?,
    onClick: (LocalDate) -> Unit,
    onNext: (LocalDate) -> Unit,
) {
    var year by remember {
        mutableIntStateOf(start.year)
    }
    var month by remember {
        mutableIntStateOf(start.monthNumber)
    }
    Column(
        modifier = modifier
    ) {
        // 현재 날짜 정보
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_calendar_title, year, month),
                modifier = Modifier.padding(8.dp),
                fontFamily = pretendard,
                color = Gray800,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val iconButtonModifier = Modifier
                    .border(1.dp, Gray200, RoundedCornerShape(8.dp))
                    .size(32.dp)
                IconButton(
                    onClick = {
                        if (month == 1) {
                            year -= 1
                            month = 12
                        } else {
                            month -= 1
                        }
                    },
                    modifier = iconButtonModifier
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
                }
                IconButton(
                    onClick = {
                        if (month == 12) {
                            year += 1
                            month = 1
                        } else {
                            month += 1
                        }
                    },
                    modifier = iconButtonModifier
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        // 요일 헤더 추가
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = day,
                        fontFamily = pretendard,
                        color = Gray500,
                        fontSize = 12.sp
                    )
                }

            }
        }

        val daysInMonth = DateUtil.getDaysOfMonthWithOffset(year, month)

        val itemHeight = 41.dp
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(itemHeight * 6)
        ) {
            items(
                items = daysInMonth
            ) { day ->
                val itemModifier = Modifier.height(itemHeight)
                if (day == null) {
                    Spacer(
                        modifier = itemModifier,
                    )
                } else {
                    TextButton(
                        onClick = {
                            onClick(day)
                        },
                        modifier = itemModifier,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Box {
                            if (selected == day) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.Black, CircleShape)
                                        .size(itemHeight)
                                )
                            }
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = day.dayOfMonth.toString(),
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = if (selected == day) Color.White else Gray800
                            )
                        }
                    }
                }

            }
        }

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            onClick = {
                onNext(selected!!)
            },
            enabled = selected != null,
            title = if (selected == null) {
                stringResource(R.string.date_calendar_btn_disabled)
            } else {
                stringResource(
                    R.string.date_calendar_btn_enabled,
                    selected.monthNumber,
                    selected.dayOfMonth,
                    DateUtil.getWeekString(localDate = selected)
                )
            },
            radius = 16.dp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.height(54.dp)
        )
    }
}


@Preview(showBackground = true, apiLevel = 33, device = "spec:width=411dp,height=891dp")
@Composable
private fun CalendarViewPreview() {
    var selected: LocalDate? by remember {
        mutableStateOf(
            null
        )
    }
    CalendarView(
        selected = selected,
        onClick = {
            selected = it
        },
        onNext = {

        }
    )
}


@Preview(
    showBackground = true, apiLevel = 33, device = "spec:width=411dp,height=891dp",
    showSystemUi = true
)
@Composable
private fun CalendarModalPreview() {
    var show by remember {
        mutableStateOf(true)
    }
    var selected: LocalDate? by remember {
        mutableStateOf(
            null
        )
    }
    Column(
        modifier = Modifier.safeContentPadding()
    ) {
        Text(selected.toString())
        Button(
            onClick = {
                show = true
            }
        ) {
            Text("Test")
        }
        if (show) {
            CalendarModal(
                onDismiss = {
                    show = false
                },
                onClick = {
                    selected = it
                }
            )
        }
    }
}