package com.sooum.data.util

import android.content.Context
import android.net.Uri
import com.sooum.domain.util.UriConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UriConverterImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :UriConverter {

    override fun saveContentUriToTempFile(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val tempFile = kotlin.io.path.createTempFile(
                directory = context.cacheDir.toPath(),
                prefix = "temp_",
                suffix = ".tmp"
            ).toFile()

            inputStream.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}