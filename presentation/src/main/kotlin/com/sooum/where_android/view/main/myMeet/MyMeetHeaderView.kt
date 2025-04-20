package com.sooum.where_android.view.main.myMeet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.Filter
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
internal fun MyMeetHeaderView(
    openDrawer: () -> Unit,
    filter: Filter,
    updateFilter: (Filter) -> Unit,
    showFilter: Boolean
) {
    fun Filter.getDisplay(): String {
        return when (this) {
            is Filter.Time -> "시간순"
            is Filter.Create -> "생성순"
        }
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.image_social_where_logo_noncolor),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Primary600),
                    modifier = Modifier
                        .width(52.7.dp)
                        .height(24.05.dp)
                )
            }

            IconButton(
                onClick = openDrawer
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_drawer),
                    contentDescription = "drawer icon"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                ),
        ) {
            Text(
                text = "내 모임",
                fontSize = 24.sp,
                fontFamily = pretendard,
                color = Gray800,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            if (showFilter) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Row(
                        modifier = Modifier
                            .width(56.dp)
                            .height(20.dp)
                            .align(Alignment.End)
                            .clickable {
                                expanded = true
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = filter.getDisplay(),
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = GrayScale700
                        )
                        Icon(
                            painter = painterResource(R.drawable.icon_filter_arrow),
                            contentDescription = null,
                            tint = GrayScale700,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        containerColor = Color.White
                    ) {
                        listOf(
                            Filter.Time,
                            Filter.Create
                        ).forEach { filterItem ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = filterItem.getDisplay(),
                                        color = GrayScale900,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontFamily = pretendard
                                    )
                                },
                                onClick = {
                                    updateFilter(filterItem)
                                    expanded = false
                                },
                                modifier = Modifier.height(43.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
@Preview(showSystemUi = false, backgroundColor = 0xFFFFFFFF)
private fun MyMeetHeaderPreview() {
    Surface(
        color = Color.White
    ) {
        MyMeetHeaderView(
            openDrawer = {},
            filter = Filter.Time,
            updateFilter = {},
            showFilter = true
        )
    }
}