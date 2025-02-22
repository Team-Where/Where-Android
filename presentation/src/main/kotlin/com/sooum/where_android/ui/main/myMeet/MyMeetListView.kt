package com.sooum.where_android.ui.main.myMeet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale300
import com.sooum.where_android.theme.GrayScale500
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
internal fun MyMeetListView(
    meetDetailList: List<MeetDetail>,
    navigationGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (meetDetailList.isEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.image_main_mymeet_none),
                    contentDescription = null,
                    modifier = Modifier
                        .width(81.45.dp)
                        .height(87.dp)
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.my_meet_no_meet),
                    fontFamily = pretendard,
                    color = GrayScale500,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(20.dp))

                OutlinedButton(
                    onClick = navigationGuide,
                    modifier = Modifier.height(40.dp),
                    border = BorderStroke(1.dp, GrayScale300)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = Primary600
                        )
                        Text(
                            text = stringResource(R.string.my_meet_no_meet_btn),
                            fontFamily = pretendard,
                            color = Primary600,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    } else {

    }
}

internal class MeetDetailParameterProvider() : PreviewParameterProvider<List<MeetDetail>> {
    override val values: Sequence<List<MeetDetail>>
        get() = sequenceOf(
            emptyList()
        )
}

@Composable
@Preview(
    backgroundColor = 0xFFFFFFFF, showSystemUi = true, showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=320"
)
private fun MyMeetListViewPreView(
    @PreviewParameter(MeetDetailParameterProvider::class) data: List<MeetDetail>

) {
    Surface(
        color = Color.White
    ) {
        MyMeetListView(
            meetDetailList = data,
            navigationGuide = {},
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
    }
}