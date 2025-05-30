package com.sooum.where_android.view.main.home.friendList.modal

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
import androidx.compose.runtime.remember
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
import com.sooum.domain.model.Friend
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.view.widget.CircleProfileView
import com.sooum.where_android.view.widget.FavoriteIconButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailModal(
    friend: Friend,
    onDismiss: () -> Unit,
    navigationMeetDetail: () -> Unit,
    updateFavorite: (
        id: Int,
        success: () -> Unit,
        fail: (msg: String) -> Unit
    ) -> Unit
) {

    val scope = rememberCoroutineScope()
    val loadingScreenProvider = remember {
        LoadingScreenProvider(scope)
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            !loadingScreenProvider.showLoading
        }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState
    ) {
        Box {
            ProfileDetailContent(
                friend = friend,
                onClose = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                },
                navigationMeetDetail = navigationMeetDetail,
                updateFavorite = {
                    loadingScreenProvider.startLoading()
                    updateFavorite(
                        it,
                        {
                            loadingScreenProvider.stopLoading()
                        },
                        {
                            loadingScreenProvider.stopLoading()
                        }
                    )
                }
            )
            if (loadingScreenProvider.showLoading) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(402.dp)
                        .background(Color.Gray.copy(alpha = 0.5f)),
                    msg = null
                )
            }
        }
    }
}

@Composable
private fun ProfileDetailContent(
    friend: Friend,
    onClose: () -> Unit,
    navigationMeetDetail: () -> Unit,
    updateFavorite: (id: Int) -> Unit,
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
            .navigationBarsPadding(),
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
                isFavorite = friend.isFavorite,
                toggleFavorite = {
                    updateFavorite(friend.id)
                }
            )
        }

        Column(
            modifier = Modifier.width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            CircleProfileView(
                friend.image,
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
                            R.string.friend_list_detail_meet_count, friend.meetList.size
                        ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard,
                        color = Primary600
                    )
                }
                Text(
                    text = friend.name,
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
        friend = Friend(7, "냠냠쩝쩝", true),
        {},
        {},
        { _, _, _ -> }
    )
}


@Preview(showBackground = true)
@Composable
private fun ProfileDetailContentPreview() {
    ProfileDetailContent(
        friend = Friend(7, "냠냠쩝쩝", true),
        {},
        {},
        { _ -> }
    )
}
