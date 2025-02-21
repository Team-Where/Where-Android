package com.sooum.where_android.ui.main.meetList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600

@Composable
fun MeetListView(
    openDrawer :() -> Unit
) {
    MeetListViewContent(
        openDrawer = openDrawer
    )
}


@Composable
private fun MeetListViewContent(
    openDrawer :() -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.image_where_logo_noncolor),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Primary600)
                )
            }

            IconButton(
                onClick = openDrawer
            ) {
                Icon(Icons.AutoMirrored.Filled.List, null)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MeetListViewContentPreview() {
    MeetListViewContent(
        openDrawer = {},
        modifier = Modifier
            .safeDrawingPadding()
            .padding(12.dp)
    )
}