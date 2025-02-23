package com.sooum.where_android.ui.main.newMeet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.where_android.theme.GrayScale100

@Composable
fun NewMeetAddView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val maxLength = 16
        var tempTitle by remember {
            mutableStateOf("")
        }
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(GrayScale100)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("사진 추가")
            }
        }
        Column {
            TextField(
                value = tempTitle,
                onValueChange = {
                    if (tempTitle.length <= maxLength) {
                        tempTitle = it
                    }
                },
                placeholder = {
                    Text("모임이름을 입력해주세요.")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(

                )
            )
            Text(
                "(${tempTitle.length}/maxLength)"
            )
        }

    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun NewMeetAddViewPreview() {
    NewMeetAddView()
}