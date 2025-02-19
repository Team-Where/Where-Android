package com.sooum.where_android.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sooum.domain.model.User
import com.sooum.where_android.ui.friendList.FriedListView
import kotlinx.serialization.Serializable


sealed class BottomNavigationType {
    @Serializable
    data object MeetList :BottomNavigationType()

    @Serializable
    data object FriendsList : BottomNavigationType()
}


@Composable
fun MainScreenView(
    modifier: Modifier = Modifier,
    userList: List<User>
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation(
                navBackStackEntry = navBackStackEntry,
                navigation = { type ->
                    navController.navigate(type)
                }
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavigationType.MeetList
        ) {
            composable<BottomNavigationType.MeetList>() {
                Column {
                    Text(navBackStackEntry?.destination?.route ?: "")
                    Text(BottomNavigationType.MeetList.toString())
                    Text(BottomNavigationType.FriendsList.toString())
                }

            }
            composable<BottomNavigationType.FriendsList>() {
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