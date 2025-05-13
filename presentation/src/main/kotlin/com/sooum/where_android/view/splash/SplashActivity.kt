package com.sooum.where_android.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.where_android.databinding.ActivitySplashBinding
import com.sooum.where_android.nextActivity
import com.sooum.where_android.view.getInviteData
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        intent.getInviteData()
        setContentView(binding.root)

        lifecycleScope.launch {
            splashViewModel.checkSplash(
                needUpdate = {
                    showForceUpdateDialog()
                },
                complete = { dest ->
                    nextActivity(dest)
                },
                onVersionCheckFailed = {
                    finishAffinity()
                }

            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun showForceUpdateDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("업데이트 필요")
            .setMessage("새로운 버전이 출시되었습니다.\n최신 버전으로 업데이트 후 이용해 주세요.")
            .setCancelable(false)
            .setPositiveButton("업데이트") { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=$packageName")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }


}
