package com.sooum.where_android.ui.meetInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.where_android.R
import com.sooum.where_android.util.DateUtil
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun CalendarView(
    start: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
) {
    var year by remember {
        mutableIntStateOf(start.year)
    }
    var month by remember {
        mutableIntStateOf(start.monthNumber)
    }
    Column {
        // 현재 날짜 정보
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.date_calendar_title, year, month),
                modifier = Modifier.padding(8.dp)
            )
            Row {
                Button(
                    onClick = {
                        if (month == 1) {
                            year -= 1
                            month = 12
                        } else {
                            month -= 1
                        }
                    }
                ) {
                    Text("<")
                }
                Button(
                    onClick = {
                        if (month == 12) {
                            year += 1
                            month = 1
                        } else {
                            month += 1
                        }
                    }
                ) {
                    Text(">")
                }
            }
        }

        // 요일 헤더 추가
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    day,
                    Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val daysInMonth = DateUtil.getDaysOfMonthWithOffset(year, month)

        val itemHeight = 25.dp
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
                    Button(
                        onClick = {},
                        modifier = itemModifier,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = day!!.dayOfMonth.toString(),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalendarViewPreview() {
    CalendarView()
}