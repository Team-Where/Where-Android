package com.sooum.where_android.view.invite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.Schedule
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray200
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale800
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.InviteData
import com.sooum.where_android.view.widget.CoverImageView
import com.sooum.where_android.view.widget.PrimaryButton

@Composable
fun InviteBySchemeView(
    inviteData: InviteData,
    onBack: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onBack
            ) {
                Icon(Icons.Filled.Clear, null)
            }
        }
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.image_particle),
                contentDescription = null
            )
            Text(
                text = "${inviteData.name}님이 초대합니다.",
                modifier = Modifier.align(Alignment.BottomCenter),
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                color = GrayScale800,
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(27.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(386.dp)
                .border(1.dp, Gray200, RoundedCornerShape(16.dp))
                .padding(horizontal = 91.dp)
                .padding(vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoverImageView(
                inviteData.image,
                120.dp,
                16.dp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = inviteData.title,
                fontSize = 24.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                color = GrayScale900
            )
            Spacer(Modifier.height(12.dp))
            inviteData.schedule?.let { schedule: Schedule ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_calender_black),
                        contentDescription = "calender icon",
                        modifier = Modifier.size(16.dp),
                        tint = GrayScale700
                    )
                    Text(
                        text = schedule.formatDateWithDot,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = GrayScale700
                    )
                    Text(
                        text = with(schedule.hour) {
                            if (this > 12) {
                                "오후 ${this - 12}시"
                            } else {
                                "오전 ${this}시"
                            }
                        },
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = GrayScale700
                    )
                }
                Spacer(Modifier.height(24.dp))
            }
            PrimaryButton(
                onClick = onClick,
                title = "수락하기",
                modifier = Modifier.width(103.dp),
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun InviteSchemePreview() {
    InviteBySchemeView(
        InviteData(
            0,
            "2024 연말파티\uD83E\uDD42",
            "",
            "",
            "",
            "QWER",
            "u212u8n49k"
        )
    )
}