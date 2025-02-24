package com.sooum.where_android.ui.main.newMeet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale100
import com.sooum.where_android.theme.GrayScale200
import com.sooum.where_android.theme.GrayScale500
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
fun NewMeetAddView(
    modifier: Modifier = Modifier,
    title: String,
    updateTitle: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val maxLength = 16
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(GrayScale100)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
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
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun NewMeetAddViewPreview() {
    NewMeetAddView(Modifier, "123", {})
}