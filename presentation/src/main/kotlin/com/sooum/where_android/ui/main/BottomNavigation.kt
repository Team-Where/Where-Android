package com.sooum.where_android.ui.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.navigation.NavBackStackEntry
import com.sooum.where_android.R
import com.sooum.where_android.model.BottomNavigationType
import com.sooum.where_android.theme.Gray200
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard


@Composable
fun BottomNavigation(
    navBackStackEntry: NavBackStackEntry?,
    navigation: (BottomNavigationType) -> Unit = {},
) {
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Column {
        HorizontalDivider()
        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            val modifier = Modifier
                .weight(1f)
                .fillMaxHeight()

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                BottomIconButton(
                    icon = R.drawable.icon_bottom_group,
                    title = "내 모임",
                    onClick = {
                        navigation(BottomNavigationType.MeetList)
                    },
                    isSelected = currentRoute.contains(BottomNavigationType.MeetList.toString())
                )
            }
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Primary600
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                BottomIconButton(
                    icon = R.drawable.icon_bottom_friend,
                    title = "친구목록",
                    onClick = {
                        navigation(BottomNavigationType.FriendsList)
                    },
                    isSelected = currentRoute.contains(BottomNavigationType.FriendsList.toString())
                )
            }
        }
        Spacer(Modifier.height(20.dp))
    }

}

@Composable
private fun BottomIconButton(
    @DrawableRes icon: Int,
    title: String,
    onClick: () -> Unit = {},
    isSelected: Boolean = false
) {
    val color = if (isSelected) {
        Color(0xff111827)
    } else {
        Gray200
    }
    Button(
        onClick = onClick,
        enabled = !isSelected,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(icon),
                null,
                tint = color
            )
            Text(
                text = title,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(null)
}
