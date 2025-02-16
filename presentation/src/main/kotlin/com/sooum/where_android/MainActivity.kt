package com.sooum.where_android

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sooum.data.repository.UserRepositoryImpl
import com.sooum.domain.repository.UserRepository
import com.sooum.where_android.databinding.ActivityMainBinding
import com.sooum.where_android.ui.main.UserListView
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

        binding.composeView.setContent {
            val userData by userViewModel.userList.collectAsState()
            Column {
                UserListView(userData)
            }
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        userViewModel.loadUserList()
    }
}