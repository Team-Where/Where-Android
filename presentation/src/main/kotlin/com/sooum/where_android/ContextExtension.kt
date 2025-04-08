package com.sooum.where_android

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.core.net.toUri

fun Context.startMapUriOrMarket(
    uriString: String,
    packageName: String
) = startMapUriOrMarket(uriString.toUri(), packageName)

fun Context.startMapUriOrMarket(
    uri: android.net.Uri,
    packageName: String
) {
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addCategory(Intent.CATEGORY_BROWSABLE)

    val list: List<ResolveInfo> =
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if (list.isEmpty()) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "market://details?id=$packageName".toUri()
            )
        )
    } else {
        startActivity(intent)
    }
}