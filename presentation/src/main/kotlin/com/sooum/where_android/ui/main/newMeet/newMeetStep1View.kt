package com.sooum.where_android.ui.main.newMeet

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale100
import com.sooum.where_android.theme.GrayScale200
import com.sooum.where_android.theme.GrayScale500
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale800
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
fun NewMeetAddView(
    modifier: Modifier = Modifier,
    title: String,
    updateTitle: (String) -> Unit,
    type: ImageAddType?,
    updateImageType: (ImageAddType) -> Unit
) {

    val focusManager = LocalFocusManager.current

    var showPickerDialog by remember {
        mutableStateOf(false)
    }
    val pickSingleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                updateImageType(ImageAddType.Content(uri))
            }
        }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val maxLength = 16
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
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                value = title,
                onValueChange = {
                    if (it.length <= maxLength) {
                        updateTitle(it)
                    }
                },
                placeholder = {
                    Text(
                        text = "모임이름을 입력해주세요.",
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
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = if (title.isEmpty()) {
                    null
                } else {
                    {
                        IconButton(
                            onClick = {
                                updateTitle("")
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
                text = "(${title.length}/$maxLength)",
                color = GrayScale700
            )
        }
    }
    if (showPickerDialog) {
        PickerDialog(
            onDismiss = {
                showPickerDialog = false
            },
            updateImageType = updateImageType,
            requestImagePicker = {
                pickSingleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )
    }
}

@Composable
private fun PickerDialog(
    onDismiss: () -> Unit,
    requestImagePicker :() -> Unit,
    updateImageType: (ImageAddType) -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .width(310.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            val textList = listOf(
                "기본 커버 선택",
                "앨범에서 사진 선택"
            )
            val imageRes = listOf(
                R.drawable.icon_pick_default,
                R.drawable.icon_pick_image
            )
            val actionList = listOf(
                {
                    updateImageType(ImageAddType.Default)
                    onDismiss()
                },
                {
                    requestImagePicker()
                    onDismiss()
                }
            )

            repeat(2) {
                val text = textList[it]
                val res = imageRes[it]
                val action = actionList[it]
                Button(
                    onClick = action,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = GrayScale800
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(res),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = text,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun NewMeetAddViewPreview() {
    NewMeetAddView(Modifier, "123", {}, null, {})
}