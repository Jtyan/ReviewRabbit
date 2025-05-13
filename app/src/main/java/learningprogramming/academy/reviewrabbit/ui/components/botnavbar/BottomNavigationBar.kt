package learningprogramming.academy.reviewrabbit.ui.components.botnavbar


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import learningprogramming.academy.reviewrabbit.model.navigationItemList
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight

@Composable
fun ReviewRabbitBottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        contentColor = extendedLight.heroBanner.color,
        modifier = Modifier.fillMaxWidth()
    ) {
        navigationItemList.forEach {
            NavigationBarItem(
                selected = currentDestination?.route == it.navigationTab.label,
                onClick = {
                    navController.navigate(it.navigationTab.label) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = it.text
                    )
                },
//                label = {
//                    Text(
//                        text = it.text
//                    )
//                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = extendedLight.heroBanner.color,
                    unselectedTextColor = extendedLight.heroBanner.color,
                    disabledIconColor = MaterialTheme.colorScheme.background,
                    disabledTextColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewRabbitBottomNavBarPreview() {
    ReviewRabbitTheme {
        ReviewRabbitBottomNavBar(
            navController = rememberNavController()
        )
    }
}
