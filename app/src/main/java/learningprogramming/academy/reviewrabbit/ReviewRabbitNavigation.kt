package learningprogramming.academy.reviewrabbit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import learningprogramming.academy.reviewrabbit.model.NavigationTab
import learningprogramming.academy.reviewrabbit.ui.screens.CreateCompanyScreen
import learningprogramming.academy.reviewrabbit.ui.screens.HomeScreen
import learningprogramming.academy.reviewrabbit.ui.screens.UserSettingsScreen

@Composable
fun ReviewRabbitApp(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationTab.HOME.label) {
        composable(route = NavigationTab.HOME.label) {
            HomeScreen()
        }
        composable(route = NavigationTab.CREATE_NEW_COMPANY.label) {
            CreateCompanyScreen()
        }
        composable(route = NavigationTab.USER_SETTINGS.label){
            UserSettingsScreen()
        }
    }
}