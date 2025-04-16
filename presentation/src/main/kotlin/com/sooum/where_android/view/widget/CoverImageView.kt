package com.sooum.where_android.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sooum.where_android.R

@Composable
fun CoverImageView(
    src: String?,
    size: Dp,
    radius: Dp,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        src,
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(radius)),
        contentScale = ContentScale.Crop
    ) {
        val state by painter.state.collectAsState()
        if (state is AsyncImagePainter.State.Success) {
            SubcomposeAsyncImageContent()
        } else {
            Image(
                painter = painterResource(R.drawable.image_meet_default_cover),
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview
@Composable
fun MeetDetailCoverImagePreview() {
    CoverImageView(
        src = null, size = 170.dp, radius = 10.dp
    )
}