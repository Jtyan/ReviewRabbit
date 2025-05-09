package learningprogramming.academy.reviewrabbit.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import learningprogramming.academy.reviewrabbit.R
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewRabbitTopAppBar(
    isExpanded: Boolean,
    toggleExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "App Icon",
                        tint = Color.Unspecified
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { toggleExpanded(isExpanded) },
                    modifier = Modifier
                        .border(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 4.dp)
                        .size(36.dp)
                ) {
                    AnimatedIcon(isExpanded)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.surface,
            ),
            windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top)
                .add(WindowInsets(left = 30, right = 30))
        )

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopAppBarPreview() {
    ReviewRabbitTheme(dynamicColor = false, darkTheme = false) {
        ReviewRabbitTopAppBar(
            isExpanded = true,
            toggleExpanded = {}
        )
    }
}