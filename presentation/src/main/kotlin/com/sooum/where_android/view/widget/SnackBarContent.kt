package com.sooum.where_android.view.widget

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.SnackBarColor
import com.sooum.where_android.theme.pretendard

sealed class IconType(
    @DrawableRes val iconRes: Int
) {
    /**
     * 아이콘 없는 버전
     */
    data object None : IconType(0)

    /**
     * 체크 버전
     */
    data object Check : IconType(R.drawable.icon_new_meet_check)

    /**
     * 에러 버전
     */
    data object Error : IconType(0)
}

@Composable
fun SnackBarContent(
    message: String,
    iconType: IconType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 10.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SnackBarColor)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconType != IconType.None) {
            Icon(
                painter = painterResource(iconType.iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Primary600
            )
            Spacer(Modifier.width(5.dp))
        }
        Text(
            text = message,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun CheckSnackBarPreview() {
    Column {
        SnackBarContent("test", IconType.None)
        SnackBarContent("test", IconType.Check)
    }
}