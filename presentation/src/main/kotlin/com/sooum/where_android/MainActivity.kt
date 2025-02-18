package com.sooum.where_android

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.sooum.data.repository.UserRepositoryImpl
import com.sooum.domain.repository.UserRepository
import com.sooum.where_android.databinding.ActivityMainBinding
import com.sooum.where_android.ui.friendList.FriedListView
import com.sooum.where_android.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userRepository: UserRepository = UserRepositoryImpl()

    private val userViewModel: UserViewModel by viewModels(
        factoryProducer = { UserViewModel.Companion.ScoreViewModelFactory(userRepository) }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
            setContent {
                val userData by userViewModel.userList.collectAsState()
                Column(
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 6.dp
                    )
                ) {
                    FriedListView(
                        userList = userData,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
            }
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        userViewModel.loadUserList()
    }
}