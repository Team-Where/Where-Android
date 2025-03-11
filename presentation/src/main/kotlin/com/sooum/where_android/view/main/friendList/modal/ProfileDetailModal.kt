package com.sooum.where_android.view.main.friendList.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CircleProfileView
import com.sooum.where_android.view.widget.FavoriteIconButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailModal(
    user: User,
    onDismiss: () -> Unit,
    navigationMeetDetail: () -> Unit,
    updateFavorite: (id: Long, favorite: Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState
    ) {
        ProfileDetailContent(
            user = user,
            onClose = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            },
            navigationMeetDetail = navigationMeetDetail,
            updateFavorite = updateFavorite
        )
    }
}

@Composable
private fun ProfileDetailContent(
    user: User,
    onClose: () -> Unit,
    navigationMeetDetail: () -> Unit,
    updateFavorite: (id: Long, favorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(402.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 10.dp
            )
            .navigationBarsPadding()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_close),
                    contentDescription = "profile detail close"
                )
            }
            FavoriteIconButton(
                isFavorite = user.isFavorite,
                toggleFavorite = {
                    updateFavorite(user.id, !user.isFavorite)
                }
            )
        }

        Column(
            modifier = Modifier.width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            //TODO profileImage 값으로 부터 가져오도록 수정필요
            CircleProfileView(
                user.profileImage,
                size = 120.dp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color(0xffeef2ff))
                        .padding(
                            horizontal = 8.dp,
                            vertical = 2.dp
                        )
                ) {
                    Text(
                        text = stringResource(
                            R.string.friend_list_detail_meet_count, 3
                        ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard,
                        color = Primary600
                    )
                }
                Text(
                    text = user.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard,
                    color = Color.Black
                )
            }
        }

        TextButton(
            onClick = navigationMeetDetail,
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary600
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = stringResource(R.string.friend_list_detail_meet_show),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard,
                fontSize = 16.sp
            )
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileDetailModalPreview() {
    ProfileDetailModal(
        user = User(7, "냠냠쩝쩝", "", true),
        {},
        {},
        { _, _ -> }
    )
}


@Preview(showBackground = true)
@Composable
private fun ProfileDetailContentPreview() {
    ProfileDetailContent(
        user = User(7, "냠냠쩝쩝", "", true),
        {},
        {},
        { _, _ -> }
    )
}
