package com.sooum.where_android.ui.main.myMeet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
internal fun MyMeetHeaderView(
    openDrawer: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.image_where_logo_noncolor),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Primary600)
                )
            }

            IconButton(
                onClick = openDrawer
            ) {
                Icon(Icons.AutoMirrored.Filled.List, null)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "내 모임",
                fontSize = 24.sp,
                fontFamily = pretendard,
                color = Gray800,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview(showSystemUi = false, backgroundColor = 0xFFFFFFFF)
private fun MyMeetHeaderPreview() {
    Surface(
        color = Color.White
    ) {
        MyMeetHeaderView(
            openDrawer = {}
        )
    }
}