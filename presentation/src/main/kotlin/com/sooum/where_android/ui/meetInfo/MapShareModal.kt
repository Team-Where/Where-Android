package com.sooum.where_android.ui.meetInfo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard


private sealed class MapItem(
    @DrawableRes val imageRes: Int,
    @StringRes val stringRes: Int,
    val marketPackage: String,
    val scheme: Uri
) {
    data object Naver : MapItem(
        R.drawable.icon_map_naver,
        R.string.map_share_by_naver,
        "com.nhn.android.nmap",
        Uri.parse("nmap://search?")
    )

    data object Kakao : MapItem(
        R.drawable.icon_map_kakao,
        R.string.map_share_by_kakao,
        "net.daum.android.map",
        Uri.parse("kakaomap://open?page=placeSearch")
    )

    fun checkOrStart(
        context: Context
    ) {
        val intent = Intent(Intent.ACTION_VIEW, scheme)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val list: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.isEmpty()) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$marketPackage")
                )
            )
        } else {
            context.startActivity(intent)
        }
    }
}

@Composable
fun MapShareModalContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "장소 공유",
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Gray800
            )
            IconButton(
                onClick = onDismiss
            ) {
                Icon(Icons.Filled.Clear, null)
            }
        }
        Column {
            listOf(
                MapItem.Naver,
                MapItem.Kakao
            ).forEach { item ->
                Button(
                    onClick = {
                        item.checkOrStart(context)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xff282828)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(item.imageRes),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(item.stringRes),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MapShareContentPreview() {
    MapShareModalContent(
        modifier = Modifier.padding(10.dp),
        onDismiss = {}
    )
}