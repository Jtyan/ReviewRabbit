package learningprogramming.academy.reviewrabbit.util

import android.util.Base64
import android.util.Log

object Base64 {
    fun decode(image: String?): ByteArray? {
        if (image.isNullOrEmpty()) return null

        var imageData: ByteArray? = null
        try {
            val pureBase64Encoded = image.substringAfter("base64,")
            imageData = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            Log.e(
                "CoilDebug",
                "Failed to decode Base64 string.",
                e
            )
        }
        return imageData
    }
}