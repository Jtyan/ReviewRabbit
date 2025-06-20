package learningprogramming.academy.reviewrabbit.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class PasswordOutputTransformation(private val mask: Char = '\u2022') : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val originalTextLength = this.length
        if (originalTextLength > 0) {
            this.replace(0, originalTextLength, mask.toString().repeat(originalTextLength))
        }
    }
}