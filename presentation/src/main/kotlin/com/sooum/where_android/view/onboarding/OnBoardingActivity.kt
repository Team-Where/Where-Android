package com.sooum.where_android.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.where_android.databinding.ActivitySingleFrameBinding
import com.sooum.where_android.nextActivity
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.getInviteData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleFrameBinding

    @Inject
    lateinit var appManageDataStore: AppManageDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleFrameBinding.inflate(layoutInflater)
        intent.getInviteData()
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frame.id, OnBoardingFragment())
        transaction.commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun goNextActivity() {
        lifecycleScope.launch {
            appManageDataStore.setNotFirstLaunch()
            nextActivity(AuthActivity::class.java)
        }
    }
}
