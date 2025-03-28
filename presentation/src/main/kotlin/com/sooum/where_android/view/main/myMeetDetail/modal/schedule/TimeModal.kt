package com.sooum.where_android.view.main.myMeetDetail.modal.schedule

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.GrayButton
import com.sooum.where_android.view.widget.ModalHeader
import com.sooum.where_android.view.widget.PrimaryButton
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeModal(
    startData: Int? = null,
    onDismiss: () -> Unit,
    onClick: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

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
                header = "시간 선택",
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            HorizontalDivider()
            TimeView(
                startData = startData,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .navigationBarsPadding(),
                onDismiss = onDismiss,
                onClick = {
                    scope.launch {
                        async {
                            sheetState.hide()
                            onDismiss()
                        }
                        async {
                            onClick(it)
                        }
                    }
                }
            )
        }
    }
}


@Composable
private fun TimeView(
    modifier: Modifier = Modifier,
    startData: Int? = null,
    onDismiss: () -> Unit,
    onClick: (Int) -> Unit
) {
    var selectedValue1: Boolean? by remember {
        mutableStateOf(startData?.let { it > 12 })
    }
    var selectedValue2: Int? by remember {
        mutableStateOf(startData?.let { if (it > 12) it - 12 else it })
    }
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.heightIn(min = 300.dp)
        ) {
            Column {

                TimeRow(
                    title = "오전/오후",
                    placeHolder = "",
                    onValue = selectedValue1?.let {
                        if (it) {
                            "오후"
                        } else {
                            "오전"
                        }
                    },
                    dataList = listOf(false, true),
                    dataTitleList = listOf("오전", "오후"),
                    onClick = {
                        selectedValue1 = it
                    }
                )
                Spacer(Modifier.height(23.dp))
                HorizontalDivider()
                Spacer(Modifier.height(23.dp))

                TimeRow(
                    title = "시간",
                    placeHolder = "",
                    onValue = selectedValue2?.let {
                        it.toString() + "시"
                    },
                    dataList = List(12) { it + 1 },
                    dataTitleList = List(12) { "${it + 1}시" },
                    onClick = {
                        selectedValue2 = it
                    }
                )
            }

        }
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GrayButton(
                    onClick = onDismiss,
                    title = "취소",
                    radius = 16.dp,
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                )
                PrimaryButton(
                    onClick = {
                        if (selectedValue1 != null && selectedValue2 != null) {
                            var result = selectedValue2 as Int
                            if (selectedValue1 as Boolean) {
                                result += 12
                            }
                            onClick(result)
                        }
                    },
                    title = "확인",
                    radius = 16.dp,
                    fontWeight = FontWeight.Medium,
                    enabled = selectedValue1 != null && selectedValue2 != null,
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                )
            }
        }
    }
}


@Composable
private fun <T> TimeRow(
    title: String,
    placeHolder: String,
    onValue: String?,
    dataList: List<T>,
    dataTitleList: List<String>,
    onClick: (T) -> Unit
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isDropDownMenuExpanded = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(23.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(23.dp),
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

            Box {
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
                        painter = painterResource(R.drawable.icon_time_modal_more),
                        contentDescription = null,
                        tint = Gray500
                    )
                }
                val scrollState = rememberScrollState()

                val maxHeight = 220.dp

                DropdownMenu(
                    modifier = Modifier
                        .width(120.dp)
                        .heightIn(max = maxHeight)
                        .verticalScrollbar(scrollState),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false },
                    containerColor = Color.White,
                    scrollState = scrollState
                ) {

                    dataList.forEachIndexed { index, data ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = dataTitleList[index],
                                    color = GrayScale900,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    fontFamily = pretendard
                                )
                            },
                            onClick = {
                                isDropDownMenuExpanded = false
                                onClick(data)
                            },
                            modifier = Modifier.height(43.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 33, device = "spec:width=411dp,height=891dp")
@Composable
private fun TimeViewPreview() {
    TimeView(
        modifier = Modifier.padding(15.dp),
        onDismiss = {},
        onClick = {}
    )
}


fun Modifier.verticalScrollbar(
    scrollState: ScrollState,
    scrollBarWidth: Dp = 4.dp,
    minScrollBarHeight: Dp = 5.dp,
    scrollBarColor: Color = Color.Gray,
    cornerRadius: Dp = 2.dp
): Modifier = composed {
    drawWithContent {
        drawContent()

        val visibleHeight: Float = this.size.height - scrollState.maxValue
        val scrollBarHeight: Float =
            max(visibleHeight * (visibleHeight / this.size.height), minScrollBarHeight.toPx())
        val scrollPercent: Float = scrollState.value.toFloat() / scrollState.maxValue
        val scrollBarOffsetY: Float =
            scrollState.value + (visibleHeight - scrollBarHeight) * scrollPercent

        drawRoundRect(
            color = scrollBarColor,
            topLeft = Offset(this.size.width - scrollBarWidth.toPx(), scrollBarOffsetY),
            size = Size(scrollBarWidth.toPx(), scrollBarHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
    }
}