package com.sooum.where_android.ui.main.newMeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.viewmodel.NewMeetViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMeetModal(
    onDismiss: () -> Unit,
    newMeetViewModel: NewMeetViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        newMeetViewModel.clear()
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState,
        modifier = Modifier.statusBarsPadding(),
    ) {
        NewMeetContent(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 20.dp,
                end = 20.dp
            ),
            title = newMeetViewModel.newMeetData.title,
            updateTitle = newMeetViewModel::updateTitle,
            type = newMeetViewModel.newMeetData.image,
            updateImageType = newMeetViewModel::updateImage,
            onClose = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

sealed class NewMeetType(
    val order: Int,
    val title: String
) {
    data object Info : NewMeetType(1, "어떤 모임인가요?")
    data object Friend : NewMeetType(2, "파티원을 초대해요!")
}

@Composable
private fun NewMeetContent(
    modifier: Modifier,
    title: String,
    updateTitle: (String) -> Unit,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit,
    onClose: () -> Unit
) {
    var viewType: NewMeetType by remember {
        mutableStateOf(NewMeetType.Info)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) {
        launch {
            snackbarHostState.showSnackbar(
                message = "모임 이름과 사진은 생성 후에도 변경할 수 있어요.",
                duration = SnackbarDuration.Short
            )
        }
        delay(3000L)
        snackbarHostState.currentSnackbarData?.dismiss()
    }
    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {
                Button(
                    onClick = {
                        viewType = NewMeetType.Friend
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary600
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = when (viewType) {
                        is NewMeetType.Info -> {
                            title.isNotEmpty()
                        }

                        is NewMeetType.Friend -> {
                            true
                        }
                    }
                ) {
                    Text(
                        text = "다음",
                        fontFamily = pretendard,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onClose,
                    ) {
                        Icon(Icons.Filled.Clear, null)
                    }
                }
                NewMeetHeader(
                    type = viewType,
                )
                when (viewType) {
                    is NewMeetType.Info -> {
                        NewMeetAddView(
                            modifier = Modifier.padding(
                                top = 20.dp
                            ),
                            title = title,
                            updateTitle = updateTitle,
                            type = type,
                            updateImageType = updateImageType
                        )
                    }

                    is NewMeetType.Friend -> {
                        Text("123")
                    }
                }
            }
        }
    }
}

@Composable
private fun NewMeetHeader(
    type: NewMeetType
) {
    Column {
        Text(
            text = "새 모임 만들기(${type.order}/2)",
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Primary600
        )
        Spacer(
            Modifier.height(16.dp)
        )
        Text(
            text = type.title,
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun NewMeetContentPreview() {
    NewMeetContent(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = "2024 연말파티\uD83E\uDD42",
        updateTitle = {},
        type = null,
        updateImageType = {},
        onClose = {}
    )
}