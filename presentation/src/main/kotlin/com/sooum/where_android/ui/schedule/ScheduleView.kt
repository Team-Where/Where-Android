package com.sooum.where_android.ui.schedule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.widget.PrimaryButton
import kotlinx.datetime.LocalDate

@Composable
fun ScheduleView(
    modifier: Modifier
) {
    var showCalendar by remember {
        mutableStateOf(false)
    }
    var selectedDate: LocalDate? by remember {
        mutableStateOf(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row {

            }
            HorizontalDivider()
            Column {
                ScheduleRow(
                    title = "만나는 날짜",
                    placeHolder = "날짜를 선택해주세요",
                    onValue = selectedDate?.let {
                        stringResource(
                            R.string.date_calendar_selected,
                            it.year,
                            it.monthNumber,
                            it.dayOfMonth
                        )
                    },
                    onClick = {
                        showCalendar = true
                    }
                )
                HorizontalDivider()
                ScheduleRow(
                    title = "만나는 시간",
                    placeHolder = "시간을 선택해주세요",
                    onValue = null,
                    onClick = {

                    }
                )
            }
        }

        PrimaryButton(
            onClick = {},
            title = "확인"
        )
    }
    if (showCalendar) {
        CalendarModal(
            onDismiss = {
                showCalendar = false
            },
            nextBy = {
                selectedDate = it
            }
        )
    }
}

@Composable
private fun ScheduleRow(
    title: String,
    placeHolder: String,
    onValue: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .height(71.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            if (onValue == null) {
                Text(
                    text = placeHolder,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Gray500
                )
            } else {
                Text(
                    text = onValue,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color(0xff1f2937)
                )
            }
            Icon(
                painter = painterResource(R.drawable.icon_schedule_open),
                contentDescription = null,
                tint = Gray500
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun ScheduleViewPreview() {
    ScheduleView(
        modifier = Modifier
            .safeDrawingPadding()
            .padding(10.dp)
    )
}