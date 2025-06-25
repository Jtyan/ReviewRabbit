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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyResponse
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@Composable
fun CompanyLocationAndTags(
    company: CompanyResponse,
    color: Color = Color.Unspecified
) {
    val location = company.location
    val country = company.country
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Company Location",
                tint = color
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = if (location.isEmpty() && country.isEmpty()) "N/A"
                else if (location.isEmpty() || country.isEmpty()) "$location$country"
                else "${company.location}, ${company.country}",
                color = color
            )
        }
        Row(
            modifier = Modifier.padding(end = 4.dp, top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Discount,
                contentDescription = "Company Tags",
                tint = color
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = if (company.tags.isNotEmpty()) company.tags.joinToString() else "N/A",
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyLocationAndTagsPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        CompanyLocationAndTags(
            company = CompanyResponse(
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