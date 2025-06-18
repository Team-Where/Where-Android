package com.sooum.core.notification

import android.content.Intent

/**
 * AlarmOption 설정
 */
interface AlarmOption {

    fun makeIntent(): Intent

    suspend fun notificationAllowed(): Boolean
}