package learningprogramming.academy.reviewrabbit.util

import android.util.Log
import kotlinx.coroutines.delay
import java.io.IOException

suspend fun <T> retryIO(
    times: Int = 3,
    initialDelay: Long = 1000L,
    maxDelay: Long = 8000L,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) { attempt ->
        try {
            return block()
        } catch (e: IOException) {
            Log.e("retryIO", "Attempt #${attempt + 1} failed with IOException. Retrying in ${currentDelay}ms.", e)
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block()
}