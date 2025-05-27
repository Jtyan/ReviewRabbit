package learningprogramming.academy.reviewrabbit.ui.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(
    child: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 500.dp, max = 500.dp)
            .wrapContentHeight()
            .padding(16.dp)
            .border(
                color = MaterialTheme.colorScheme.outlineVariant,
                width = 1.dp,
                shape = RoundedCornerShape(5.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.scrim,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        child()
    }
}