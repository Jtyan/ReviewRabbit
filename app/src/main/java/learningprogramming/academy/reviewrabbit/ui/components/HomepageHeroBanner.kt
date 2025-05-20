package learningprogramming.academy.reviewrabbit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight

@Composable
fun HomepageHeroBanner(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(
                brush = Brush.linearGradient(
                    0.0f to extendedLight.heroBanner.colorContainer,
                    1.0f to extendedLight.heroBanner.colorContainer.copy(alpha = 0.3f),
                    start = Offset(0.0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0.0f)
                )
            )
    ) {
        Text(
            text = "Company \nReview Rabbit",
            lineHeight = 40.sp,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(24.dp).align(Alignment.CenterStart)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomepageHeroBannerPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        HomepageHeroBanner()
    }
}