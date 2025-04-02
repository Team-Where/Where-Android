package com.sooum.where_android.view.main

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.Meet
import com.sooum.domain.model.NewMeet
import com.sooum.where_android.R
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.theme.Gray200
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.main.newMeet.NewMeetModal
import kotlinx.serialization.Serializable

fun Meet.toResult() = NewMeetResult(
    id = id,
    title = title,
    image = image
)

@Serializable
data class NewMeetResult(
    val id : Int,
    val title: String,
    val image: String?
)


@Composable
fun BottomNavigation(
    navBackStackEntry: NavBackStackEntry?,
    navigation: (ScreenRoute.BottomNavigation) -> Unit = {},
    navigationResult: (NewMeetResult) -> Unit = {}
) {
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    var addNewMeetModal by remember {
        mutableStateOf(false)
    }
    Column {
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray200)
        )
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
                        navigation(ScreenRoute.BottomNavigation.MeetList)
                    },
                    isSelected = currentRoute.contains(ScreenRoute.BottomNavigation.MeetList.toString())
                )
            }
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        addNewMeetModal = true
                    },
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
                        navigation(ScreenRoute.BottomNavigation.FriendsList)
                    },
                    isSelected = currentRoute.contains(ScreenRoute.BottomNavigation.FriendsList::class.java.simpleName) || currentRoute.contains(
                        ScreenRoute.Home.FriendMeetDetail::class.java.simpleName
                    )
                )
            }
        }
        Spacer(Modifier.height(20.dp))
    }

    if (addNewMeetModal) {
        NewMeetModal(
            onDismiss = {
                addNewMeetModal = false
            },
            navigationResult = {
                navigationResult(it.toResult())
            }
        )
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
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
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
