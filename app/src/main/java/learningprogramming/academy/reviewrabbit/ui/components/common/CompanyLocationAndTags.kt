package learningprogramming.academy.reviewrabbit.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@Composable
fun CompanyLocationAndTags(
    company: CompanyApiResponse
) {
    val location = company.location
    val country = company.country
    Column {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Company Location"
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = if (location.isEmpty() && country.isEmpty()) "N/A"
                    else if (location.isEmpty() || country.isEmpty()) "$location$country"
                    else "${company.location}, ${company.country}"
            )
        }
        Row(
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Discount,
                contentDescription = "Company Tags"
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = if (company.tags.isNotEmpty()) company.tags.joinToString() else "N/A"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyLocationAndTagsPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        CompanyLocationAndTags(
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