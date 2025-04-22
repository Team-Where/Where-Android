package com.sooum.where_android.view.main.home.newMeet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.User
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.viewmodel.NewMeetType
import com.sooum.where_android.viewmodel.NewMeetViewModel
import kotlinx.coroutines.launch

/**
 * 새모임 추가 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMeetModal(
    onDismiss: () -> Unit,
    navigationResult: (NewMeetResult) -> Unit,
    newMeetViewModel: NewMeetViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        newMeetViewModel.clear()
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            false
        }
    )
    val scope = rememberCoroutineScope()
    val viewType by newMeetViewModel.viewType.collectAsState()
    val userList by newMeetViewModel.userList.collectAsState()
    var showLoading by remember {
        mutableStateOf(false)
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState,
        modifier = Modifier.statusBarsPadding(),
    ) {
        Box {
            NewMeetContent(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                    .navigationBarsPadding(),
                title = newMeetViewModel.newMeetData.title,
                updateTitle = newMeetViewModel::updateTitle,
                description = newMeetViewModel.newMeetData.description,
                updateDescription = newMeetViewModel::updateDescription,
                type = newMeetViewModel.newMeetData.image,
                userList = userList,
                updateImageType = newMeetViewModel::updateImage,
                viewType = viewType,
                goStep2 = newMeetViewModel::goStep2,
                goStepResult = {
                    showLoading = true
                    newMeetViewModel.goStepResult(
                        complete = { result ->
                            showLoading = false
                            scope.launch {
                                Log.d("JWH", "$result")
                                if (result is ActionResult.Success) {
                                    navigationResult(result.data)
                                    sheetState.hide()
                                    onDismiss()
                                } else {
                                    //TODO 에러 발생시 핸들링
                                }
                            }
                        }
                    )
                },
                inviteFriend = newMeetViewModel::inviteFriend,
                onClose = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }
            )
            if (showLoading) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f)),
                    msg = "모임 추가중입니다."
                )
            }
        }
    }
}


@Composable
private fun NewMeetContent(
    modifier: Modifier,
    title: String,
    updateTitle: (String) -> Unit,
    description: String,
    updateDescription: (String) -> Unit,
    userList: List<User>,
    viewType: NewMeetType,
    goStep2: () -> Unit,
    goStepResult: () -> Unit,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit,
    inviteFriend: (User) -> Unit,
    onClose: () -> Unit
) {

    when (viewType) {
        is NewMeetType.Info -> {
            NewMeetStep1View(
                modifier = modifier,
                type = type,
                updateImageType = updateImageType,
                title = title,
                updateTitle = updateTitle,
                description = description,
                updateDescription = updateDescription,
                nextViewType = goStep2,
                onClose = onClose
            )
        }

        is NewMeetType.Friend -> {
            NewMeetStep2View(
                modifier = modifier,
                userList = userList,
                recentUserList = userList,
                nextViewType = goStepResult,
                inviteFriend = inviteFriend,
                onClose = onClose
            )
        }
    }
}

internal class NewMeetContentParameterProvider() : PreviewParameterProvider<NewMeetType> {
    override val values: Sequence<NewMeetType>
        get() = sequenceOf(
            NewMeetType.Info,
            NewMeetType.Friend,
        )
}

@Composable
@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun NewMeetContentPreview(
    @PreviewParameter(NewMeetContentParameterProvider::class) type: NewMeetType
) {
    NewMeetContent(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = "2024 연말파티\uD83E\uDD42",
        updateTitle = {},
        type = null,
        updateImageType = {},
        viewType = type,
        goStep2 = {},
        goStepResult = {},
        userList = emptyList(),
        inviteFriend = {},
        onClose = {},
        description = "",
        updateDescription = {}
    )
}