package com.sooum.where_android.view.main.newMeet

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.NewMeet
import com.sooum.domain.model.User
import com.sooum.where_android.ui.main.newMeet.NewMeetStep2View
import com.sooum.where_android.viewmodel.NewMeetType
import com.sooum.where_android.viewmodel.NewMeetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMeetModal(
    onDismiss: () -> Unit,
    navigationResult: (NewMeet) -> Unit,
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
            ).navigationBarsPadding(),
            title = newMeetViewModel.newMeetData.title,
            updateTitle = newMeetViewModel::updateTitle,
            type = newMeetViewModel.newMeetData.image,
            userList = userList,
            updateImageType = newMeetViewModel::updateImage,
            viewType = viewType,
            nextViewType = {
                newMeetViewModel.nextViewType(
                    complete = {
                        scope.launch {
                            navigationResult(it)
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                )
            },
            inviteFriend = {

            },
            onClose = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}


@Composable
private fun NewMeetContent(
    modifier: Modifier,
    title: String,
    updateTitle: (String) -> Unit,
    userList: List<User>,
    viewType: NewMeetType,
    nextViewType: () -> Unit,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit,
    inviteFriend: (User) -> Unit,
    onClose: () -> Unit
) {

    when (viewType) {
        is NewMeetType.Info -> {
            NewMeetStep1View(
                modifier = modifier,
                title = title,
                updateTitle = updateTitle,
                type = type,
                updateImageType = updateImageType,
                nextViewType = nextViewType,
                onClose = onClose
            )
        }

        is NewMeetType.Friend -> {
            NewMeetStep2View(
                modifier = modifier,
                userList = userList,
                recentUserList = userList,
                nextViewType = nextViewType,
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
        nextViewType = {},
        userList = emptyList(),
        inviteFriend = {},
        onClose = {}
    )
}