package com.sooum.where_android.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.domain.model.User
import com.sooum.where_android.ui.friendList.FriedListView

@Composable
fun MainScreenView(
    modifier: Modifier = Modifier,
    userList: List<User>
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation()
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                )
        ) {
            FriedListView(userList = userList)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenViewPreview() {
    MainScreenView(
        Modifier,
        emptyList()
    )
}