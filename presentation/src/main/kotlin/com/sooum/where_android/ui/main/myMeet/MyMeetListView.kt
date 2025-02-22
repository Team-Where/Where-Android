package com.sooum.where_android.ui.main.myMeet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.main.meetDetail.GroupDataParameterProvider

@Composable
internal fun MyMeetListView(
    meetDetailList: List<MeetDetail>,
    modifier: Modifier = Modifier
) {
   if (meetDetailList.isEmpty()) {
       Column(
           modifier = modifier,
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Column {

               Text("아직 모임이 없어요")
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
@Preview(backgroundColor = 0xFFFFFFFF, showSystemUi = true, showBackground = true)
private fun MyMeetListViewPreView(
    @PreviewParameter(MeetDetailParameterProvider::class) data: List<MeetDetail>

) {
    Surface(
        color = Color.White
    ) {
        MyMeetListView(
            meetDetailList = data,
            modifier = Modifier.fillMaxSize().safeDrawingPadding()
        )
    }
}