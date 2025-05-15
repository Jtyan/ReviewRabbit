package learningprogramming.academy.reviewrabbit.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
    onClick = onClick,
    shape = RoundedCornerShape(size = 5.dp),
    colors = ButtonColors(
    containerColor = MaterialTheme.colorScheme.tertiary,
    contentColor = MaterialTheme.colorScheme.onTertiary,
    disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
    disabledContentColor = MaterialTheme.colorScheme.secondary,
    ),
    elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
    content = {
        Text(
            text = text,
            fontWeight = FontWeight.Normal
        )
    },
    modifier = Modifier
    .width(300.dp)
    .padding(24.dp)
    )
}