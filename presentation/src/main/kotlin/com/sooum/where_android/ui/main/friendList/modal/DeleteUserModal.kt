package com.sooum.where_android.ui.main.friendList.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Red500
import com.sooum.where_android.theme.pretendard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteUserModal(
    onDismiss :() -> Unit,
    onDelete: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
    ) {
        DeleteUserModalContent(
            onDelete = {
                onDelete()
                onDismiss()
            }
        )
    }
}


@Composable
private fun DeleteUserModalContent(
    onDelete :() -> Unit = {}
) {
    Column(
        modifier = Modifier.height(150.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray100
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(R.string.friend_list_delete),
                color = Red500,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DeleteUserModalPreview() {
    DeleteUserModal({},{})
}

@Preview(showBackground = true)
@Composable
private fun DeleteUserModalContentPreview() {
    DeleteUserModalContent()
}

