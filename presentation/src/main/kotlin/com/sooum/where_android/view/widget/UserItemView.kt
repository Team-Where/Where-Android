package com.sooum.where_android.view.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.GrayScale100
import com.sooum.where_android.theme.GrayScale300
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

sealed class UserViewType {

    /**
     * 즐겨찾기 관련
     * @param[isFavorite] 즐겨첮기 여부
     */
    data class Favorite(
        val isFavorite: Boolean
    ) : UserViewType()

    /**
     * 삭제 필요시
     */
    data object Delete : UserViewType()

    /**
     * 3점 옵션
     */
    data object Option : UserViewType()

    /**
     * 초대 관련
     */
    data object Invite : UserViewType()

    /**
     * 초대 대기중
     */
    data object Waiting : UserViewType()

    /**
     * 아무것도 없음
     */
    data object Nothing : UserViewType()
}

@Composable
fun UserItemView(
    user: User,
    type: UserViewType,
    userClickAction: (() -> Unit)? = null,
    iconClickAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val height = when (type) {
        is UserViewType.Favorite, is UserViewType.Delete -> {
            50.dp
        }

        is UserViewType.Option, is UserViewType.Invite, is UserViewType.Waiting, is UserViewType.Nothing -> {
            40.dp
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .then(
                if (userClickAction == null) {
                    Modifier
                } else {
                    Modifier.clickable(
                        onClick = userClickAction
                    )
                }
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            //TODO profileImage 값으로 부터 가져오도록 수정필요
            CircleProfileView(
                profileUrl = user.profileImage,
                size = height
            )

            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.height(height),
                verticalArrangement = if (type is UserViewType.Invite) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.Center
                }
            ) {
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    textAlign = TextAlign.Center
                )
                if (type is UserViewType.Invite) {
                    Text(
                        text = "N번 만남",
                        color = Color(0xff9ca3af),
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        fontFamily = pretendard,
                    )
                }
            }
        }
        val enterAni =
            slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                -fullWidth / 3
            } + fadeIn(
                animationSpec = tween(durationMillis = 200)
            )

        val exitAni =
            slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                200
            } + fadeOut()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            androidx.compose.animation.AnimatedVisibility(
                visible = type is UserViewType.Favorite,
                enter = enterAni,
                exit = exitAni
            ) {
                if (type is UserViewType.Favorite) {
                    FavoriteIconButton(
                        isFavorite = type.isFavorite,
                        toggleFavorite = {
                            iconClickAction?.invoke()
                        }
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = type is UserViewType.Delete,
                enter = enterAni,
                exit = exitAni
            ) {
                if (type is UserViewType.Delete) {
                    IconButton(
                        onClick = {
                            iconClickAction?.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_delete),
                            contentDescription = "user delete icon",
                            tint = Gray500
                        )
                    }
                }
            }


            androidx.compose.animation.AnimatedVisibility(
                visible = type is UserViewType.Invite,
                enter = enterAni,
                exit = exitAni
            ) {
                if (type is UserViewType.Invite) {
                    val shape = RoundedCornerShape(8.dp)
                    val buttonModifier = Modifier
                        .height(32.dp)
                    var alreadyAdd by remember {
                        mutableStateOf(false)
                    }

                    Button(
                        onClick = {
                            alreadyAdd = true
                            iconClickAction?.invoke()
                        },
                        modifier = buttonModifier,
                        enabled = !alreadyAdd,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            disabledContainerColor = GrayScale100
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        ),
                        shape = shape,
                        border = BorderStroke(1.dp, GrayScale300),
                    ) {
                        AnimatedVisibility(alreadyAdd) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_check),
                                    contentDescription = null,
                                    tint = Primary600,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "완료",
                                    color = Primary600,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    fontFamily = pretendard
                                )
                            }
                        }
                        AnimatedVisibility(!alreadyAdd) {
                            Text(
                                text = "초대",
                                color = Primary600,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontFamily = pretendard
                            )
                        }
                    }
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = type is UserViewType.Option,
                enter = enterAni,
                exit = exitAni
            ) {
                if (type is UserViewType.Option) {
                    IconButton(
                        onClick = {
                            iconClickAction?.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_3dot),
                            contentDescription = "user option icon",
                            tint = Gray600
                        )
                    }
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = type is UserViewType.Waiting,
                enter = enterAni,
                exit = exitAni
            ) {
                if (type is UserViewType.Waiting) {
                    Row(
                        modifier = Modifier
                            .height(25.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Gray100)
                            .padding(
                                vertical = 4.dp,
                                horizontal = 8.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "대기중",
                            color = Gray500,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = pretendard,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    val user = User(1, "냠냠쩝쩝", "")
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        listOf(
            UserViewType.Favorite(isFavorite = true),
            UserViewType.Favorite(isFavorite = false),
            UserViewType.Delete,
            UserViewType.Option,
            UserViewType.Invite,
            UserViewType.Waiting
        ).forEach { type ->
            UserItemView(
                user = user,
                type = type,
                iconClickAction = {
                    println("Click $type")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}