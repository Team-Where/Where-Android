package com.sooum.where_android.view.widget

import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.Red500
import com.sooum.where_android.theme.SnackBarColor
import com.sooum.where_android.theme.pretendard

sealed class IconType(
    @DrawableRes val iconRes: Int = 0,
    val color: Color = Color.Transparent
) {
    /**
     * 아이콘 없는 버전
     */
    data object None : IconType()

    /**
     * 체크 버전
     */
    data object Check : IconType(
        R.drawable.icon_new_meet_check,
        Primary600
    )

    /**
     * 에러 버전
     */
    data object Error : IconType(
        R.drawable.icon_warning,
        Red500
    )
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
                tint = iconType.color
            )
            Spacer(Modifier.width(8.dp))
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
        SnackBarContent("일반", IconType.None)
        SnackBarContent("체크 있는놈", IconType.Check)
        SnackBarContent("에러 표시", IconType.Error)
    }
}

/**
 * XML 호환을 위한 CustomSnackBar
 * ```
 * CustomSnackBar.make(binding.root, "test", IconType.Check).show()
 *```
 */
class CustomSnackBar(
    view: View,
    private val message: String,
    private val iconType: IconType,
    duration: Int = 2000
) {

    companion object {

        fun make(
            view: View,
            message: String,
            iconType: IconType,
            duration: Int = 2000
        ) = CustomSnackBar(view, message, iconType, duration)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", duration)
    private val snackBarLayout = snackBar.view as ViewGroup

    init {
        addComposeView()
    }

    private fun addComposeView() {
        with(snackBarLayout) {
            //기본 배경 제거
            setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
            //기본 패딩 삭제
            setPadding(0, 0, 0, 0)
            val composeView = ComposeView(snackBarLayout.context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    SnackBarContent(message, iconType = iconType)
                }
            }
            // 기본 View와 ComposeView를 함께 사용
            addView(composeView)
        }
    }

    fun show() {
        snackBar.show()
    }
}