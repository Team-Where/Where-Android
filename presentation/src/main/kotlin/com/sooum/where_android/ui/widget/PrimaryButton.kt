package com.sooum.where_android.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    title: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary600
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(
            text = title,
            fontFamily = pretendard,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}