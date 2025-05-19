package learningprogramming.academy.reviewrabbit.ui.components.searchfilters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.model.FilterTabs
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.AnimatedIcon
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewRabbitViewModel

@Composable
fun SearchFilters(
    viewModel: ReviewRabbitViewModel
) {
    val companyFiltersData = viewModel.companyFilters.collectAsState().value

    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                color = MaterialTheme.colorScheme.outlineVariant,
                width = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .verticalScroll(rememberScrollState()),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.scrim,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchFilterCategory(
                title = "Search Filters",
                child = {
                    FilterTabs.entries.forEach() { filterTabs ->
                        val itemsToShow: List<String> = when (filterTabs) {
                            FilterTabs.LOCATIONS -> companyFiltersData.locations
                            FilterTabs.COUNTRIES -> companyFiltersData.countries
                            FilterTabs.INDUSTRIES -> companyFiltersData.industries
                            FilterTabs.TAGS -> companyFiltersData.tags
                        }
                        SearchFilterCategory(
                            title = filterTabs.label,
                            child = {
                                itemsToShow.forEach { item ->
                                    SearchFilterContentWithCheckbox(
                                        filterItem = item
                                    )
                                }
                            }
                        )
                    }
                    CustomButton(
                        text = "Filter",
                        onClick = {},
                        modifier = Modifier.width(300.dp).padding(24.dp)
                    )
                }
            )
        }
    }
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
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = !checked },
            modifier = modifier.size(36.dp)
        )
        Text(
            text = filterItem,
            fontSize = 13.sp
        )
    }
}