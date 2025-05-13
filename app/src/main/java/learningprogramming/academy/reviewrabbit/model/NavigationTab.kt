package learningprogramming.academy.reviewrabbit.model

import learningprogramming.academy.reviewrabbit.R

enum class NavigationTab(val label: String) {
    HOME ("Home"),
    CREATE_NEW_COMPANY("New Company"),
    USER_SETTINGS("Settings")
}

val navigationItemList = listOf(
    NavigationItemContent(
        navigationTab = NavigationTab.HOME,
        icon = R.drawable.home_icon,
        text = NavigationTab.HOME.label
    ),
    NavigationItemContent(
        navigationTab = NavigationTab.CREATE_NEW_COMPANY,
        icon = R.drawable.building_icon,
        text = NavigationTab.CREATE_NEW_COMPANY.label
    ),
    NavigationItemContent(
        navigationTab = NavigationTab.USER_SETTINGS,
        icon = R.drawable.user_icon,
        text = NavigationTab.USER_SETTINGS.label
    )
)

data class NavigationItemContent(
    val navigationTab: NavigationTab,
    val icon: Int,
    val text: String
)