package learningprogramming.academy.reviewrabbit.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale

object Base64Decoder {
    fun base64ToByteArray(image: String?): ByteArray? {
        if (image.isNullOrEmpty()) return null
        return try {
            val pureBase64Encoded = image.substringAfter("base64,")
            Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            Log.e("Base64Decoder", "Failed to decode Base64 string.", e)
            null
        }
    }

    fun uriToBase64(context: Context, uri: Uri?): String? {
        return try {

            val inputStream =
                if (uri != null) context.contentResolver.openInputStream(uri) else return null
            val imageBitmap = BitmapFactory.decodeStream(inputStream)
            val imageBitmapAfterResize = resizeImage(imageBitmap)

            ByteArrayOutputStream().use { baos ->
                imageBitmapAfterResize.compress(Bitmap.CompressFormat.JPEG, 85, baos)

                val imageBytes = baos.toByteArray()

                Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }

        } catch (e: Exception) {
            Log.e("Base64Decoder", "Failed to convert URI to base64", e)
            null
        }
    }

    private fun resizeImage(image: Bitmap, maxWidth: Int = 200, maxHeight: Int = 200): Bitmap {
        val width = image.width
        val height = image.height

        if (width <= maxWidth && height <= maxHeight) {
            return image
        }

        val aspRatio = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = maxWidth
            newHeight = (newWidth / aspRatio).toInt()
        } else {
            newHeight = maxHeight
            newWidth = (newHeight * aspRatio).toInt()
        }

        return image.scale(newWidth, newHeight)
    }
}