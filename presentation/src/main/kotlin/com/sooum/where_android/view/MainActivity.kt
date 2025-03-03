package com.sooum.where_android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.sooum.where_android.databinding.ActivityMainBinding
import com.sooum.where_android.ui.main.MainScreenView
import dagger.hilt.android.AndroidEntryPoint


val LocalActivity = compositionLocalOf<MainActivity> {
    error("CompositionLocal LocalActivity not present")
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
            setContent {
                CompositionLocalProvider(
                    LocalActivity provides this@MainActivity
                ) {
                    MainScreenView(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        setContentView(binding.root)
    }
}