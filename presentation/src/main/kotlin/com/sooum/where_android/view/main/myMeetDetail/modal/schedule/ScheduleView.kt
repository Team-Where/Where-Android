package com.sooum.where_android.view.main.myMeetDetail.modal.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sooum.domain.model.Schedule
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import kotlinx.datetime.LocalDate

fun Schedule.toLocalDate(): LocalDate {
    return LocalDate(year, month, day)
}

@Composable
fun ScheduleView(
    modifier: Modifier,
    onBack: () -> Unit,
    onNewSchedule: (Schedule) -> Unit,
    prevSchedule: Schedule? = null
) {
    var showCalendar by remember {
        mutableStateOf(false)
    }
    var selectedDate: LocalDate? by remember {
        mutableStateOf(prevSchedule?.toLocalDate())
    }

    var showTime by remember {
        mutableStateOf(false)
    }
    var selectedTime: Int? by remember {
        mutableStateOf(prevSchedule?.time)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
            ) {
                Text(
                    text = prevSchedule?.let {
                        "일정 수정"
                    } ?: "일정 등록",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = pretendard,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Filled.Clear, null)
                }

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
                    onValue = selectedTime?.let { hour ->
                        if (hour > 12) {
                            "오후 ${hour - 12}시"
                        } else {
                            "오전 ${hour}시"
                        }
                    },
                    onClick = {
                        showTime = true
                    }
                )
            }
        }

        PrimaryButton(
            onClick = {
                onNewSchedule(
                    Schedule(
                        selectedDate!!.year,
                        selectedDate!!.monthNumber,
                        selectedDate!!.dayOfMonth,
                        selectedTime!!
                    )
                )
            },
            title = "확인",
            enabled = selectedDate != null && selectedTime != null,
            radius = 16.dp
        )
    }
    if (showCalendar) {
        CalendarModal(
            startData = selectedDate,
            onDismiss = {
                showCalendar = false
            },
            onClick = {
                selectedDate = it
            }
        )
    }
    if (showTime) {
        TimeModal(
            startData = selectedTime,
            onDismiss = {
                showTime = false
            },
            onClick = {
                selectedTime = it
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
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(71.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                horizontalArrangement = Arrangement.spacedBy(10.dp)
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
}

private class ScheduleParameterProvider() : PreviewParameterProvider<Schedule?> {
    override val values: Sequence<Schedule?>
        get() = sequenceOf(
            null,
            Schedule(2025, 3, 8, 23)
        )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun ScheduleViewPreview(
    @PreviewParameter(ScheduleParameterProvider::class) data: Schedule?
) {
    ScheduleView(
        modifier = Modifier
            .safeDrawingPadding()
            .padding(horizontal = 10.dp),
        prevSchedule = data,
        onBack = {},
        onNewSchedule = {}
    )
}


class ScheduleFragment : Fragment() {

    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                val myMeet by myMeetDetailViewModel.meetDetail.collectAsState()
                ScheduleView(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .navigationBarsPadding()
                        .padding(10.dp),
                    prevSchedule = myMeet?.schedule?.isDataOn(),
                    onBack = {
                        findNavController().popBackStack()
                    },
                    onNewSchedule = myMeetDetailViewModel::newSchedule
                )
            }
        }
    }
}