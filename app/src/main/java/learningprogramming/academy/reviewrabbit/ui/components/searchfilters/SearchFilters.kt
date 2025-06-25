package learningprogramming.academy.reviewrabbit.ui.components.searchfilters

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.model.FilterTabs
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomCard
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.AnimatedIcon
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel

@Composable
fun SearchFilters(
    homeScreenViewModel: HomeScreenViewModel,
    onFilterClick: (listOfFilters: CompanyFilters) -> Unit
) {
    val companyFilters = homeScreenViewModel.companyFilters.collectAsState().value
    val listOfFilters = homeScreenViewModel.listOfFilters.collectAsState().value

    LaunchedEffect(listOfFilters) {
        Log.i("SearchFilterUI", listOfFilters.toString())
    }

    CustomCard(
        child = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchFilterCategory(
                    title = "Search Filters",
                    child = {
                        FilterTabs.entries.forEach() { filterTabs ->
                            val itemsToShow = when (filterTabs) {
                                FilterTabs.LOCATIONS -> {
                                    companyFilters.locations
                                }

                                FilterTabs.COUNTRIES -> {
                                    companyFilters.countries
                                }

                                FilterTabs.INDUSTRIES -> {
                                    companyFilters.industries
                                }

                                FilterTabs.TAGS -> {
                                    companyFilters.tags
                                }
                            }
                            SearchFilterCategory(
                                title = filterTabs.label,
                                child = {
                                    itemsToShow.forEach { item ->
                                        SearchFilterContentWithCheckbox(
                                            filterItem = item,
                                            isChecked = when (filterTabs) {
                                                FilterTabs.LOCATIONS -> listOfFilters.locations.contains(item)
                                                FilterTabs.COUNTRIES -> listOfFilters.countries.contains(item)
                                                FilterTabs.INDUSTRIES -> listOfFilters.industries.contains(item)
                                                FilterTabs.TAGS -> listOfFilters.tags.contains(item)
                                            },
                                            onCheckboxClick = { homeScreenViewModel.toggleFilter(item, filterTabs)}
                                        )
                                    }
                                }
                            )
                        }
                    }
                )
                CustomButton(
                    text = "Filter",
                    onClick = { onFilterClick(listOfFilters) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(24.dp)
                )
            }
        }
    )
}


@Composable
fun SearchFilterCategory(
    title: String,
    child: @Composable () -> Unit
) {
    var isSearchFilterCategoryExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            Text(
                text = title
            )
            IconButton(onClick = {
                isSearchFilterCategoryExpanded = !isSearchFilterCategoryExpanded
            }) {
                AnimatedIcon(
                    targetState = isSearchFilterCategoryExpanded,
                    iconIfTrue = Icons.Filled.Remove,
                    iconIfFalse = Icons.Filled.Add,
                    contentDescription = "${if (isSearchFilterCategoryExpanded) "Collapse" else "Expand"} $title filter"
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        AnimatedVisibility(
            visible = isSearchFilterCategoryExpanded,
            enter = slideInVertically()
                    + expandVertically(expandFrom = Alignment.Top)
                    + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (isSearchFilterCategoryExpanded) {
                    child()
                }
            }
        }
    }
}

@Composable
fun SearchFilterContentWithCheckbox(
    filterItem: String,
    isChecked: Boolean,
    onCheckboxClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckboxClick(filterItem)
            },
            modifier = modifier.size(36.dp)
        )
        Text(
            text = filterItem,
            fontSize = 13.sp
        )
    }
}