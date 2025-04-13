package com.sooum.data.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun File?.createPart() : MultipartBody.Part {
    if (this != null) {
        val requestFile = asRequestBody("image/*".toMediaTypeOrNull())
        return  MultipartBody.Part.createFormData("image", name, requestFile)
    } else {
        val emptyImageRequestBody = ByteArray(0).toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", null, emptyImageRequestBody)
    }
}