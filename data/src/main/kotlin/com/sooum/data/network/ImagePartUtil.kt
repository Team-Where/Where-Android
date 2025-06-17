package com.sooum.data.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun File?.createPart(
    key: String = "image"
): MultipartBody.Part {
    if (this != null) {
        val requestFile = asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, name, requestFile)
    } else {
        val emptyBody = byteArrayOf().toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, "", emptyBody)
    }
}