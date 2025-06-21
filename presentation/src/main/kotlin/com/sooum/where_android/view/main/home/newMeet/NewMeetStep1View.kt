package com.sooum.where_android.view.main.home.newMeet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale100
import com.sooum.where_android.theme.GrayScale200
import com.sooum.where_android.theme.GrayScale500
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.common.modal.ImagePickerDialog
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.view.widget.SnackBarContent
import com.sooum.where_android.viewmodel.NewMeetType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewMeetStep1View(
    modifier: Modifier = Modifier,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit,
    title: String,
    updateTitle: (String) -> Unit,
    description: String,
    updateDescription: (String) -> Unit,
    nextViewType: () -> Unit,
    onClose: () -> Unit
) {
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
            SnackbarHost(snackbarHostState) { data ->
                SnackBarContent(
                    message = data.visuals.message,
                    iconType = IconType.None
                )
            }
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
                PrimaryButton(
                    onClick = nextViewType,
                    enabled = title.isNotEmpty(),
                    title = NewMeetType.Info.buttonTitle
                )
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
                NewMeetHeader(type = NewMeetType.Info)
                NewMeetStep1ViewContent(
                    modifier = Modifier.padding(
                        top = 20.dp
                    ),
                    title = title,
                    updateTitle = updateTitle,
                    type = type,
                    updateImageType = updateImageType,
                    description = description,
                    updateDescription = updateDescription
                )
            }
        }
    }
}

@Composable
private fun NewMeetStep1ViewContent(
    modifier: Modifier = Modifier,
    title: String,
    updateTitle: (String) -> Unit,
    description: String,
    updateDescription: (String) -> Unit,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var showPickerDialog by remember {
        mutableStateOf(false)
    }

    if (showPickerDialog) {
        ImagePickerDialog(
            onDismiss = {
                showPickerDialog = false
            },
            updateImageType = updateImageType
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .size(120.dp),
            onClick = {
                showPickerDialog = true
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GrayScale100
            ),
            contentPadding = if (type == null) {
                ButtonDefaults.ContentPadding
            } else {
                PaddingValues(0.dp)
            }
        ) {
            when (type) {
                is ImageAddType.Default -> {
                    Image(
                        painter = painterResource(R.drawable.image_meet_default_cover),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                }

                is ImageAddType.Content -> {
                    AsyncImage(
                        model = type.uri,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                is ImageAddType.Contents -> {

                }

                null -> {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.icon_camera),
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Text(
                            text = "사진 추가",
                            color = GrayScale600,
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }
        }
        Step1TextField(
            text = title,
            onNewValue = updateTitle,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            maxLength = 16
        )
        Spacer(Modifier.height(32.dp))
        Step1TextField(
            text = description,
            onNewValue = updateDescription,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            maxLength = 30
        )
    }
}

@Composable
private fun Step1TextField(
    text :String,
    onNewValue :(String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLength :Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onNewValue(it)
                }
            },
            placeholder = {
                Text(
                    text = "모임 이름을 입력해주세요.",
                    color = GrayScale600,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            },
            textStyle = TextStyle(
                fontFamily = pretendard,
                color = GrayScale900,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Primary600,
                unfocusedIndicatorColor = GrayScale200
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = if (text.isEmpty()) {
                null
            } else {
                {
                    IconButton(
                        onClick = {
                            onNewValue("")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_clear),
                            contentDescription = null,
                            tint = GrayScale500
                        )
                    }
                }
            }
        )
        Text(
            text = "(${text.length}/$maxLength)",
            color = GrayScale700
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun NewMeetAddViewPreview() {
    NewMeetStep1View(Modifier, null, {}, "", {}, "", {}, {}, {})
}