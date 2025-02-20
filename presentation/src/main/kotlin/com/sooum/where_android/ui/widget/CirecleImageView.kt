package com.sooum.where_android.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.sooum.where_android.R

@Composable
fun CircleImageView(
    size :Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painterResource(R.drawable.test_profile),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .then(modifier)
    )
}