package com.sooum.where_android.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.SubcomposeAsyncImage
import com.sooum.where_android.R

@Composable
fun CircleProfileView(
    profileUrl: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val profileModifier = Modifier
        .size(size)
        .clip(CircleShape)
        .then(modifier)

    if (profileUrl.isEmpty()) {
        Image(
            painterResource(R.drawable.test_profile),
            contentDescription = null,
            modifier = profileModifier
        )
    } else {
        SubcomposeAsyncImage(
            model = profileUrl,
            contentDescription = null,
            modifier = profileModifier
        )
    }
}