package com.sooum.where_android.ui.main.newMeet

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.where_android.ui.main.NewMeetResult
import com.sooum.where_android.ui.widget.PrimaryButton

@Composable
fun NewMeetResultView(
    result: NewMeetResult,
    close: () -> Unit = {},
    navigationDetail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = close
            ) {
                Icon(Icons.Filled.Clear, null)
            }
        }

        Column {

        }

        Row {
            PrimaryButton(
                onClick = navigationDetail,
                title = "모임방으로 이동"
            )
        }
    }
}

@Composable
@Preview
fun NewMeetResultPreview() {
    NewMeetResultView(
        NewMeetResult(
            "fdfd",
            null
        )
    )
}