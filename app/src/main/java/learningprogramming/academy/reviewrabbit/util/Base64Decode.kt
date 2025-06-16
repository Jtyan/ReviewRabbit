package learningprogramming.academy.reviewrabbit.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log

object Base64Decoder {
    fun stringToByteArray(image: String?): ByteArray? {
        if (image.isNullOrEmpty()) return null

        return try {
            val pureBase64Encoded = image.substringAfter("base64,")
            Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            Log.e("Base64Decoder", "Failed to decode Base64 string.", e)
            null
        }
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                Base64.encodeToString(bytes, Base64.DEFAULT)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Base64Decoder", "Failed to convert URI to base64", e)
            null
        }
    }
}