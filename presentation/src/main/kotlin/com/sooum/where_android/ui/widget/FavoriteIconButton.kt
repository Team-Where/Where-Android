package com.sooum.where_android.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray300

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    toggleFavorite: () -> Unit
) {
    IconButton(
        onClick = toggleFavorite
    ) {
        Icon(
            painter = painterResource(
                if (isFavorite) {
                    R.drawable.icon_favorite_on
                } else {
                    R.drawable.icon_favorite_off
                }
            ),
            contentDescription = "user favorite icon",
            tint = if (isFavorite) {
                Color(0xfffbbf24)
            } else {
                Gray300
            }
        )
    }
}

@Preview
@Composable
private fun FavoriteIconButtonPreview() {
    Column {
        FavoriteIconButton(true) { }
        FavoriteIconButton(false) { }
    }
}