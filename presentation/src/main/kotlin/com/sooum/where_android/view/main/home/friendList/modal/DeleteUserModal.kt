package com.sooum.where_android.view.main.home.friendList.modal

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sooum.where_android.R
import com.sooum.where_android.view.common.modal.DeleteModalContent

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
        DeleteModalContent(
            onDelete = {
                onDelete()
                onDismiss()
            },
            modifier = Modifier.navigationBarsPadding(),
            buttonText = stringResource(R.string.friend_list_delete)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DeleteUserModalPreview() {
    DeleteUserModal({},{})
}

