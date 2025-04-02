package com.sooum.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.sooum.domain.util.UriConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.min

class UriConverterImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UriConverter {

    override fun saveContentUriToTempFile(uri: Uri): File? {
        return resizeImageAndSave(uri,300,300)
    }

    private fun resizeImageAndSave(imageUri: Uri, maxWidth: Int, maxHeight: Int): File? {
        val bitmap = resizeImage(imageUri, maxWidth, maxHeight) ?: return null
        val cacheDir = context.cacheDir
        val file = File(cacheDir, "resized_image_${System.currentTimeMillis()}.jpg")

        return try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 이미지를 조정한다.
     */
    private fun resizeImage(imageUri: Uri, maxWidth: Int, maxHeight: Int): Bitmap? {
        val inputStream = context.contentResolver.openInputStream(imageUri) ?: return null
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }

        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream.close()

        val originalWidth = options.outWidth
        val originalHeight = options.outHeight

        if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
            return BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
        }

        val ratio = min(maxWidth.toFloat() / originalWidth, maxHeight.toFloat() / originalHeight)
        val targetWidth = (originalWidth * ratio).toInt()
        val targetHeight = (originalHeight * ratio).toInt()

        options.inJustDecodeBounds = false
        options.inSampleSize =
            calculateInSampleSize(originalWidth, originalHeight, targetWidth, targetHeight)

        val resizedBitmap = BitmapFactory.decodeStream(
            context.contentResolver.openInputStream(imageUri),
            null,
            options
        )
        return resizedBitmap?.let { Bitmap.createScaledBitmap(it, targetWidth, targetHeight, true) }
    }

    /**
     * 이미지 비율 계산
     */
    private fun calculateInSampleSize(
        origWidth: Int,
        origHeight: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        var inSampleSize = 1
        if (origHeight > reqHeight || origWidth > reqWidth) {
            val halfHeight = origHeight / 2
            val halfWidth = origWidth / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}