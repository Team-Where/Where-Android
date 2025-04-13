package com.sooum.where_android.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sooum.where_android.databinding.ActivityAuthBinding
import com.sooum.where_android.R
import com.sooum.where_android.view.getInviteData
import com.sooum.where_android.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        intent.getInviteData()
        setContentView(binding.root)

        navigateToFragment(
            fragment = SocialLoginFragment(),
            addToBackStack = false
        )

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.flame, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun nextActivity() {
        Intent(this, MainActivity::class.java).apply {
            putExtras(intent)
        }.also {
            startActivity(it)
        }
        finish()
    }

}