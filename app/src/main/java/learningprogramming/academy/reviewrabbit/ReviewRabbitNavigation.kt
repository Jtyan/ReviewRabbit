package learningprogramming.academy.reviewrabbit

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import learningprogramming.academy.reviewrabbit.model.NavigationTab
import learningprogramming.academy.reviewrabbit.ui.screens.CreateCompanyScreen
import learningprogramming.academy.reviewrabbit.ui.screens.HomeScreen
import learningprogramming.academy.reviewrabbit.ui.screens.UserSettingsScreen
import learningprogramming.academy.reviewrabbit.viewmodels.ReviewRabbitViewModel

@Composable
fun ReviewRabbitApp(
    navController: NavHostController
) {
    val viewModel: ReviewRabbitViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = NavigationTab.HOME.label) {
        composable(route = NavigationTab.HOME.label) {
            HomeScreen(viewModel)
        }
        composable(route = NavigationTab.CREATE_NEW_COMPANY.label) {
            CreateCompanyScreen()
        }
        composable(route = NavigationTab.USER_SETTINGS.label){
            UserSettingsScreen()
        }
    }
}