package learningprogramming.academy.reviewrabbit.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@Composable
fun CustomTextField(
    textFieldState: TextFieldState = rememberTextFieldState(),
    text: String,
    placeholder: String = "",
    required: Boolean,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    outputTransformation: OutputTransformation? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row {
            if (required) {
                Text(
                    text = "*",
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
            Text(
                text = text
            )
        }
        TextField(
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text(text = placeholder) },
            trailingIcon = trailingIcon,
            outputTransformation = outputTransformation,
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                focusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant
                ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        CustomTextField(
            text = "Email",
            placeholder = "Your Email",
            required = true
        )
    }
}
