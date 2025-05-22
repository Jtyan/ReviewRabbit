package learningprogramming.academy.reviewrabbit.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import learningprogramming.academy.reviewrabbit.util.Base64


@Composable
fun LazyColumnCompanyItem(
    company: CompanyApiResponse,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 500.dp, max = 500.dp)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                val imageData = remember(company.image) { Base64.decode(company.image) }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageData)
                        .placeholder(R.drawable.company_logo_placeholder)
                        .fallback(R.drawable.company_logo_placeholder)
                        .error(R.drawable.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = company.name,
                    modifier = Modifier
                        .size(125.dp)
                        .padding(12.dp)
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = company.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            CompanyLocationAndTags(company = company)
            CustomButton(
                text = "Reviews",
                onClick = { onClick(company.id) },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .align(Alignment.End)
                    .width(150.dp)
                    .padding(top = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyColumnCompanyItemPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        LazyColumnCompanyItem(
            company = CompanyApiResponse(
                id = 1,
                name = "Lazy Programmers",
                url = "www.google.com",
                image = "",
                location = "London",
                country = "United Kingdom",
                industry = "Tech",
                tags = listOf("IT", "Tech")
            ),
            onClick = {}
        )
    }
}