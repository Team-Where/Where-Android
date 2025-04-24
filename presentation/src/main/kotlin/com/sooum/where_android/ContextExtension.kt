package com.sooum.where_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

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

/**
 * [dest]에 해당하는 Activity로 이동하고, 현재 activity는 종료한다.
 */
fun Activity.nextActivity(
    dest: Class<*>,
) {
    Intent(this, dest).apply {
        putExtras(intent)
    }.also {
        startActivity(it)
    }
    finish()
}

fun Context.showSimpleToast(
    msg: String
) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSimpleToast(
    msg: String
) {
    requireContext().showSimpleToast(msg)
}