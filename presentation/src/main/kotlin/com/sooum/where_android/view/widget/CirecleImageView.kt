package com.sooum.where_android.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sooum.where_android.R

@Composable
fun CircleProfileView(
    profileUrl: String?,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val profileModifier = Modifier
        .size(size)
        .clip(CircleShape)
        .then(modifier)

    SubcomposeAsyncImage(
        model = profileUrl,
        contentDescription = null,
        modifier = profileModifier,
        contentScale = ContentScale.Crop
    ) {
        val state by painter.state.collectAsState()
        if (state is AsyncImagePainter.State.Success) {
            SubcomposeAsyncImageContent()
        } else {
            Image(
                painterResource(R.drawable.image_profile_default_cover),
                contentDescription = null,
                modifier = profileModifier
            )
        }
    }
}