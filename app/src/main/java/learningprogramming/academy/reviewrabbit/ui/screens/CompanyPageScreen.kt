package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import learningprogramming.academy.reviewrabbit.R
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.ui.components.common.CompanyLocationAndTags
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.Base64

@Composable
fun CompanyPageHeroBanner(
    company: CompanyApiResponse,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    0.1f to MaterialTheme.colorScheme.primaryContainer,
                    1.0f to extendedLight.heroBanner.colorContainer.copy(alpha = 0.3f),
                    start = Offset(0.0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0.0f)
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            val imageData = remember(company.image) { Base64.decode(company.image) }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageData)
                    .placeholder(R.drawable.company_logo_placeholder)
                    .fallback(R.drawable.company_logo_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = company.name,
                modifier = Modifier.size(120.dp)

            )
            Text(
                text = company.name,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            CompanyLocationAndTags(
                company = company,
                color = MaterialTheme.colorScheme.secondary
            )
            CustomButton(
                text = "Add Review",
                onClick = {},
                containerColor = extendedLight.addReview.colorContainer,
                modifier = Modifier.width(220.dp).padding(top = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyPageHeroBannerPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        CompanyPageHeroBanner(
            company = CompanyApiResponse(
                id = 1,
                name = "Lazy Programmers",
                url = "www.google.com",
                image = "",
                location = "London",
                country = "United Kingdom",
                industry = "Tech",
                tags = listOf("IT", "Tech")
            )
        )
    }
}
