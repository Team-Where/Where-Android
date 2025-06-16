package com.sooum.where_android

import android.content.Intent
import com.sooum.core.notification.alarm.AlarmReceiver
import com.sooum.domain.model.ShareResult
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.share.MapShareResultActivity
import kotlinx.serialization.json.Json

/**
 * 스카마 혹은 앱링크로 으로 실행되었는지 확인
 */
fun Intent.checkAppScheme(): ScreenRoute.HomeRoute.InviteByCode? {
    val scheme = data?.scheme
    val host = data?.host
    val paths = data?.pathSegments
    val name = data?.getQueryParameter("name")
    if (name != null) {
        if (scheme == "https" && host == "audiwhere.shop" && paths?.size == 2) {
            val code = paths.last()
            return ScreenRoute.HomeRoute.InviteByCode(name, code)
        }

        if (scheme == "audiwhere" && host == "invite" && paths?.size == 1) {
            val code = paths.last()
            return ScreenRoute.HomeRoute.InviteByCode(name, code)
        }
    }
    return null
}

/**
 * 알림을 클릭하여 실행한 경우
 */
fun Intent.checkAlarmScheme(): Int? {
    if (action?.startsWith(AlarmReceiver.ACTION_PREFIX) == true) {
        val id = getIntExtra(AlarmReceiver.MEET_ID, -1)
        return id.takeIf { it > 0 }
    }
    return null
}

fun Intent.parseMapShareResult(): ShareResult? {
    return extras?.getString(MapShareResultActivity.SHARE_RESULT)?.let { data ->
        Json.decodeFromString<ShareResult>(data)
    }
}