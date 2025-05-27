package learningprogramming.academy.reviewrabbit.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import learningprogramming.academy.reviewrabbit.R
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.ui.components.common.CompanyLocationAndTags
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomCard
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.Base64
import learningprogramming.academy.reviewrabbit.viewmodels.CompanyReviewViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel
import org.ocpsoft.prettytime.PrettyTime
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale


@Composable
fun CompanyPage(
    homeScreenViewModel: HomeScreenViewModel,
    companyReviewViewModel: CompanyReviewViewModel,
    companyId: Int
) {
    LaunchedEffect(companyId) {
        homeScreenViewModel.getCompanyById(companyId)
        companyReviewViewModel.getReviewSummary(companyId = companyId.toLong())
    }

    val selectedCompanyState by homeScreenViewModel.selectedCompany.collectAsState()
    val selectedCompany = selectedCompanyState
    val reviewSummary by companyReviewViewModel.reviewSummary.collectAsState()
    if (selectedCompany != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            CompanyPageHeroBanner(
                company = selectedCompany
            )
            ReviewSummary(
                reviewSummary = reviewSummary,
                onClick = { companyReviewViewModel.generatedReviewSummary(companyId.toLong()) }
            )
        }
    }
}

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
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            CompanyLocationAndTags(
                company = company,
                color = MaterialTheme.colorScheme.secondary
            )
            CustomButton(
                text = "Add Review",
                onClick = {},
                containerColor = extendedLight.addReview.colorContainer,
                contentColor = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .width(180.dp)
                    .padding(top = 12.dp)
            )
        }
    }
}


@Composable
fun ReviewSummary(
    reviewSummary: ReviewSummaryApiResponse,
    onClick: () -> Unit
) {
    CustomCard(
        child = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Review Summary",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = reviewSummary.contents,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                if (reviewSummary.created.isNotEmpty()) {
                    val timestamp = Date.from(Instant.parse(reviewSummary.created))
                    val prettyTime = PrettyTime(Locale.getDefault())
                    val displayTime = prettyTime.format(timestamp)

                    Text(
                        text = "Generated $displayTime",
                        fontSize = 12.sp
                    )
                }
                if (reviewSummary.created.isEmpty() || Instant.now().isAfter(Instant.parse(reviewSummary.created).plus(1, ChronoUnit.DAYS))) {
                    CustomButton(
                        text = "Generate Summary",
                        onClick = onClick,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .wrapContentWidth()
                    )
                }
            }
        }
    )
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

@Preview(showBackground = true)
@Composable
fun CompanyPageReviewSummaryPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        ReviewSummary(
            reviewSummary = ReviewSummaryApiResponse(
                companyId = 0,
                contents = "No generated review summary yet",
                created = "2025-05-23T14:45:22.003036Z"
            ),
            onClick = {}
        )
    }
}