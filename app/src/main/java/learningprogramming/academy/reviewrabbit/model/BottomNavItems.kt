package learningprogramming.academy.reviewrabbit.model

import learningprogramming.academy.reviewrabbit.R

enum class BottomNavItems(
    val label: String,
    val route: String
) {
    HOME(label = "Home", route = "home_route"),
    CREATE_NEW_COMPANY(label = "New Company", route = "new_company_route"),
    USER_SETTINGS(label = "Settings", route = "settings_route")
}

val navigationItemList = listOf(
    NavigationItemContent(
        bottomNavItems = BottomNavItems.HOME,
        icon = R.drawable.home_icon,
        text = BottomNavItems.HOME.label
    ),
    NavigationItemContent(
        bottomNavItems = BottomNavItems.CREATE_NEW_COMPANY,
        icon = R.drawable.building_icon,
        text = BottomNavItems.CREATE_NEW_COMPANY.label
    ),
    NavigationItemContent(
        bottomNavItems = BottomNavItems.USER_SETTINGS,
        icon = R.drawable.user_icon,
        text = BottomNavItems.USER_SETTINGS.label
    )
)

data class NavigationItemContent(
    val bottomNavItems: BottomNavItems,
    val icon: Int,
    val text: String
)