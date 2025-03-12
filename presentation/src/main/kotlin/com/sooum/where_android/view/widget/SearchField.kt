package com.sooum.where_android.view.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray400
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.pretendard

@Composable
fun SearchField(
    searchValue :String,
    onValueChange :(String) -> Unit,
    modifier: Modifier,
    placeHolder :String
) {
    TextField(
        value = searchValue,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = {
            Icon(Icons.Filled.Search, null)
        },
        trailingIcon = if (searchValue.isEmpty()) {
            null
        } else {
            {
                IconButton(
                    onClick = {
                        onValueChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_clear),
                        contentDescription = "search item clear",
                        tint = Gray400
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = pretendard,
                fontSize = 16.sp,
                color = Gray500
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Gray100,
            unfocusedContainerColor = Gray100,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    )
}