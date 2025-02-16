package com.sooum.where_android.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray300
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
    ) : UserViewType() {

        @DrawableRes
        fun getImageSrc(): Int {
            return if (isFavorite) {
                R.drawable.icon_favorite_on
            } else {
                R.drawable.icon_favorite_off
            }
        }

        @DrawableRes
        fun getTintColor(): Color {
            return if (isFavorite) {
                Color(0xfffbbf24)
            } else {
                Gray300
            }
        }
    }

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
     * @param[alreadyAdd] 이미 초대 됨 여부
     */
    data class Invite(
        val alreadyAdd: Boolean
    ) : UserViewType()
}

@Composable
fun UserItemView(
    user: User,
    type: UserViewType,
    clickAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            //TODO profileImage 값으로 부터 가져오도록 수정필요
            Image(
                painterResource(R.drawable.test_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = user.name,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontFamily = pretendard
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            when (type) {
                is UserViewType.Favorite -> {
                    IconButton(
                        onClick = {
                            clickAction?.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(type.getImageSrc()),
                            contentDescription = "user favorite icon",
                            tint = type.getTintColor()
                        )
                    }
                }

                is UserViewType.Delete -> {
                    IconButton(
                        onClick = {
                            clickAction?.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_delete),
                            contentDescription = "user delete icon",
                            tint = Gray500
                        )
                    }
                }

                is UserViewType.Invite -> {
                    val shape = RoundedCornerShape(8.dp)
                    val buttonModifier = Modifier
                            .height(32.dp)

                    if (type.alreadyAdd) {
                        Button(
                            onClick = {
                                clickAction?.invoke()
                            },
                            modifier = buttonModifier,
                            enabled = false,
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = GrayScale100
                            ),
                            contentPadding = PaddingValues(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),
                            shape = shape,
                            border = BorderStroke(1.dp, GrayScale300)
                        ) {
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
                    } else {
                        Button(
                            onClick = {
                                clickAction?.invoke()
                            },
                            modifier = buttonModifier,
                            contentPadding = PaddingValues(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = shape,
                            border = BorderStroke(1.dp, GrayScale300)
                        ) {
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

                is UserViewType.Option -> {
                    IconButton(
                        onClick = {
                            clickAction?.invoke()
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
            UserViewType.Invite(alreadyAdd = true),
            UserViewType.Invite(alreadyAdd = false)
        ).forEach { type ->
            UserItemView(
                user = user,
                type = type,
                clickAction = {
                    println("Click $type")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}