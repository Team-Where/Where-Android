package com.sooum.where_android.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.widget.GrayButton
import com.sooum.where_android.ui.widget.ModalHeader
import com.sooum.where_android.ui.widget.PrimaryButton
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        mutableStateOf(startData?.let { it > 12 } ?: false)
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

            Column {
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
                DropdownMenu(
                    modifier = Modifier
                        .wrapContentSize(),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    dataList.forEachIndexed { index, data ->
                        DropdownMenuItem(
                            text = {
                                Text(text = dataTitleList[index])
                            },
                            onClick = {
                                isDropDownMenuExpanded = false
                                onClick(data)
                            }
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
