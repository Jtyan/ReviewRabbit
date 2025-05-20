package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import learningprogramming.academy.reviewrabbit.ui.components.HomepageHeroBanner
import learningprogramming.academy.reviewrabbit.ui.components.LazyColumnCompanyItem
import learningprogramming.academy.reviewrabbit.ui.components.searchfilters.SearchFilters
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewRabbitViewModel

@Composable
fun HomeScreen(
    viewModel: ReviewRabbitViewModel
) {
    val listOfCompanies by viewModel.listOfCompanies.collectAsState()

    val lazyListState = rememberLazyListState()

    val isScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0
        }
    }
    val isBannerVisible = !isScrolled

    Column {
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

        SearchFilters(viewModel)

        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOfCompanies) {
                LazyColumnCompanyItem(it)
            }
        }
    }

}