package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.runtime.Composable
import learningprogramming.academy.reviewrabbit.ui.components.searchfilters.SearchFilters
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewRabbitViewModel

@Composable
fun HomeScreen(
    viewModel: ReviewRabbitViewModel
) {
    SearchFilters(viewModel)
}