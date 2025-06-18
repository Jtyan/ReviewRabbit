package learningprogramming.academy.reviewrabbit.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import learningprogramming.academy.reviewrabbit.R
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.model.ReviewCategories
import learningprogramming.academy.reviewrabbit.model.ReviewCategoriesAndStars
import learningprogramming.academy.reviewrabbit.ui.components.common.CompanyLocationAndTags
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomCard
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.Base64Decoder
import learningprogramming.academy.reviewrabbit.viewmodels.CompanyReviewViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewUiState
import org.ocpsoft.prettytime.PrettyTime
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale


@Composable
fun CompanyPage(
    companyReviewViewModel: CompanyReviewViewModel,
    companyId: Int
) {
    var addReviewField by rememberSaveable { mutableStateOf(false) }


    val selectedCompanyState by companyReviewViewModel.selectedCompany.collectAsState()
    val selectedCompany = selectedCompanyState
    val reviewSummary by companyReviewViewModel.reviewSummary.collectAsState()
    val listOfReviews by companyReviewViewModel.listOfReviews.collectAsState()
    val reviewUiState = companyReviewViewModel.reviewUiState.collectAsState().value

    LaunchedEffect(companyId) {
        companyReviewViewModel.getCompanyById(companyId)
        companyReviewViewModel.getReviewSummary(companyId = companyId.toLong())
        companyReviewViewModel.displayReviews(companyId = companyId.toLong())

    }
    LaunchedEffect(reviewUiState) {
        if (reviewUiState is ReviewUiState.Success) {
            companyReviewViewModel.displayReviews(companyId.toLong())
        }
    }

    if (selectedCompany != null) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                CompanyPageHeroBanner(
                    company = selectedCompany,
                    onAddReviewClick = { addReviewField = true }
                )
            }
            item {
                ReviewSummary(
                    reviewSummary = reviewSummary,
                    onClick = { companyReviewViewModel.generatedReviewSummary(companyId.toLong()) }
                )
            }
            if (addReviewField) {
                item {
                    AddReviewCard(
                        companyId = companyId,
                        companyReviewViewModel = companyReviewViewModel,
                        onCancelClick = { addReviewField = false }
                    )
                }
            }
            items(listOfReviews) {
                ReviewCard(companyReview = it)
            }

            item {
                InvitePeopleCard(
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun CompanyPageHeroBanner(
    company: CompanyApiResponse,
    onAddReviewClick: () -> Unit,
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
            val imageData =
                remember(company.image) { Base64Decoder.base64ToByteArray(company.image) }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageData)
                    .placeholder(R.drawable.company_logo_placeholder)
                    .fallback(R.drawable.company_logo_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = company.name,
                contentScale = ContentScale.Crop,
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
                onClick = onAddReviewClick,
                containerColor = extendedLight.addReview.colorContainer,
                contentColor = Color.Unspecified,
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
    val isReviewSummaryCreated = reviewSummary.created.isNotEmpty()
    val isReviewOneDayOld = isReviewSummaryCreated && Instant.now()
        .isAfter(
            Instant.parse(reviewSummary.created)
                .plus(1, ChronoUnit.DAYS)
        )

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
                if (isReviewSummaryCreated) {
                    val timestamp = Date.from(Instant.parse(reviewSummary.created))
                    val prettyTime = PrettyTime(Locale.getDefault())
                    val displayTime = prettyTime.format(timestamp)

                    Text(
                        text = "Generated $displayTime",
                        fontSize = 12.sp
                    )
                }
                if (!isReviewSummaryCreated || isReviewOneDayOld) {
                    CustomButton(
                        text = "Generate Summary",
                        onClick = onClick,
                        modifier = Modifier
                            .wrapContentWidth()
                    )
                }
            }
        }
    )
}

@Composable
fun AddReviewCard(
    companyId: Int,
    companyReviewViewModel: CompanyReviewViewModel,
    onCancelClick: () -> Unit
) {
    val reviewUiState = companyReviewViewModel.reviewUiState.collectAsState().value
    val newReview = companyReviewViewModel.newReview.collectAsState().value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val reviewText = rememberTextFieldState()

    LaunchedEffect(Unit) {
        companyReviewViewModel.updateNewReview(newReview.copy(companyId = companyId))
    }

    LaunchedEffect(reviewUiState) {
        when (reviewUiState) {
            is ReviewUiState.Success -> {
                Toast.makeText(context, reviewUiState.message, Toast.LENGTH_SHORT).show()
                companyReviewViewModel.resetReviewUiState()
                onCancelClick()
            }

            is ReviewUiState.Error -> {
                Toast.makeText(context, reviewUiState.message, Toast.LENGTH_SHORT).show()
                companyReviewViewModel.resetReviewUiState()
            }

            else -> {}
        }
    }

    CustomCard(
        child = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Column {
                    for (category in ReviewCategories.entries) {
                        AddReviewCategoriesAndStars(
                            category = category,
                            onStarClick = { rating ->
                                companyReviewViewModel.updateCategoryRating(category, rating)
                            }
                        )
                    }
                }
                CustomTextField(
                    textFieldState = reviewText,
                    text = "Your review(supports Markdown)",
                    placeholder = "Write your review here",
                    lineLimits = TextFieldLineLimits.MultiLine(),
                    required = true,
                    textFieldModifier = Modifier
                        .height(175.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    CustomButton(
                        text = "Post Review",
                        onClick = {
                            companyReviewViewModel.updateNewReview(newReview.copy(review = reviewText.text.toString().trim()))
                            companyReviewViewModel.submitReview()
                        },
                    )
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { onCancelClick() },
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun AddReviewCategoriesAndStars(
    category: ReviewCategories,
    onStarClick: (rating: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var ratingValue by remember { mutableIntStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${category.label}:"
        )
        Row(
            modifier.padding(bottom = 8.dp)
        ) {
            for (num in 1..5) {
                Icon(
                    imageVector = if (num <= ratingValue) Icons.Default.Star else Icons.Default.StarOutline,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.clickable {
                        ratingValue = num
                        Log.i("CompanyPageScreen", "Category: ${category.id} Star number : $num")
                        onStarClick(num)
                    }
                )
            }
        }
    }
}

@Composable
fun ReviewCard(
    companyReview: ReviewApiResponse
) {
    val userId = 3L

    val reviewCategoriesAndStars = remember(companyReview) {
        listOf(
            ReviewCategoriesAndStars(
                label = ReviewCategories.WOULD_RECOMMEND.label,
                stars = companyReview.wouldRecommend
            ),
            ReviewCategoriesAndStars(
                label = ReviewCategories.MANAGEMENT.label,
                stars = companyReview.management
            ),
            ReviewCategoriesAndStars(
                label = ReviewCategories.CULTURE.label,
                stars = companyReview.culture
            ),
            ReviewCategoriesAndStars(
                label = ReviewCategories.SALARY.label,
                stars = companyReview.salary
            ),
            ReviewCategoriesAndStars(
                label = ReviewCategories.BENEFITS.label,
                stars = companyReview.benefits
            ),
        )
    }

    CustomCard(
        borderColor = if (companyReview.userId == userId) Color.Blue else MaterialTheme.colorScheme.outlineVariant,
        child = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                reviewCategoriesAndStars.forEach() {
                    ReviewItemAndStars(
                        category = it.label, stars = it.stars,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                Text(
                    text = companyReview.review,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (companyReview.created.isNotEmpty()) {
                    val timestamp = Date.from(Instant.parse(companyReview.created))
                    val prettyTime = PrettyTime(Locale.getDefault())
                    val displayTime = prettyTime.format(timestamp)

                    Text(
                        text = "Posted $displayTime",
                        fontSize = 12.sp
                    )
                }
                if (companyReview.userId == userId) {
                    Text(
                        text = "Your Review",
                        fontSize = 12.sp
                    )
                }
            }
        }
    )
}

@Composable
fun ReviewItemAndStars(
    category: String,
    stars: Int,
    modifier: Modifier = Modifier
) {
    var numberOfStars = stars

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$category:"
        )
        Row {
            while (numberOfStars > 0) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondaryContainer
                )
                numberOfStars--
            }
        }
    }
}

@Composable
fun InvitePeopleCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        child = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Do you represent this company? \nInvite people to leave reviews."
                )
                CustomButton(
                    text = "Invite people",
                    onClick = onClick,
                    modifier = Modifier.padding(top = 16.dp)
                )
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
            ),
            onAddReviewClick = {}
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

@Preview(showBackground = true)
@Composable
fun CompanyPageReviewItemPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        ReviewCard(
            companyReview = ReviewApiResponse(
                id = 0,
                companyId = 2L,
                userId = 3L,
                management = 3,
                culture = 4,
                salary = 5,
                benefits = 2,
                wouldRecommend = 2,
                review = "This is a review about this company",
                created = "2025-05-23T14:45:22.003036Z",
                updated = "2025-05-23T14:45:22.003036Z"
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CompanyPageInvitePeoplePreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        InvitePeopleCard(
            onClick = {}
        )
    }
}