package com.sooum.domain.util

import android.net.Uri
import java.io.File

interface UriConverter {
    fun saveContentUriToTempFile(uri: Uri): File?
}