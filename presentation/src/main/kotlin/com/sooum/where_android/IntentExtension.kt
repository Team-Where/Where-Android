package com.sooum.where_android

import android.content.Intent
import com.sooum.where_android.model.ScreenRoute

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