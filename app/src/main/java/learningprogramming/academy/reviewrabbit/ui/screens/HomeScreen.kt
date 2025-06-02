package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import learningprogramming.academy.reviewrabbit.ui.components.common.CompanyLocationAndTags
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomCard
import learningprogramming.academy.reviewrabbit.ui.components.searchfilters.SearchFilters
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.Base64decoder
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    onClick: (Int) -> Unit
) {
    val listOfCompanies by homeScreenViewModel.listOfCompanies.collectAsState()

    val lazyListState = rememberLazyListState()

    val isScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0
        }
    }
    val isBannerVisible = !isScrolled

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isBannerVisible,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut(targetAlpha = 0.3f) + shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(durationMillis = 500)
            ),
        ) {
            HomepageHeroBanner()
        }
        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                SearchFilters(homeScreenViewModel)
            }
            items(listOfCompanies) {
                LazyColumnCompanyItem(
                    it,
                    onClick = onClick
                )
            }
        }
    }
}

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
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterStart)
        )
    }
}


@Composable
fun LazyColumnCompanyItem(
    company: CompanyApiResponse,
    onClick: (Int) -> Unit
) {
    CustomCard(
        child =
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {

                        val imageData = remember(company.image) { Base64decoder.decode(company.image) }

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
                        modifier = Modifier
                            .align(Alignment.End)
                            .width(150.dp)
                            .padding(top = 20.dp)
                    )
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun HomepageHeroBannerPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        HomepageHeroBanner()
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