package learningprogramming.academy.reviewrabbit.ui.components.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 5.dp),
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.outline,
            disabledContentColor = MaterialTheme.colorScheme.outlineVariant,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        content = {
            Text(
                text = text,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        },
        enabled = enabled,
        modifier = modifier
    )
}

@Preview
@Composable
fun CustomButtonPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        CustomButton(
            text = "A Button",
            onClick = {}
        )
    }
}