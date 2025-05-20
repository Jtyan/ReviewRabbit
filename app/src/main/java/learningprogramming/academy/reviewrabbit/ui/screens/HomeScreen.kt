package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import learningprogramming.academy.reviewrabbit.ui.components.LazyColumnCompanyItem
import learningprogramming.academy.reviewrabbit.ui.components.searchfilters.SearchFilters
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewRabbitViewModel

@Composable
fun HomeScreen(
    viewModel: ReviewRabbitViewModel
) {
    val listOfCompanies = viewModel.listOfCompanies.collectAsState().value
    Column {
        SearchFilters(viewModel)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOfCompanies) {
                LazyColumnCompanyItem(it)
            }
        }
    }

}