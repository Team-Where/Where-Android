package com.sooum.domain.usecase.auth

import android.content.Context
import com.sooum.domain.util.AppVersionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class DefaultAppVersionProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : AppVersionProvider {
    override fun getVersionName(): String {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }
}