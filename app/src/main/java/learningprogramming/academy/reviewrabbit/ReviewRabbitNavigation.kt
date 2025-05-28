package learningprogramming.academy.reviewrabbit

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import learningprogramming.academy.reviewrabbit.model.ScreenRoutes
import learningprogramming.academy.reviewrabbit.ui.screens.CompanyPage
import learningprogramming.academy.reviewrabbit.ui.screens.CreateCompanyScreen
import learningprogramming.academy.reviewrabbit.ui.screens.HomeScreen
import learningprogramming.academy.reviewrabbit.ui.screens.UserSettingsScreen
import learningprogramming.academy.reviewrabbit.viewmodels.CompanyReviewViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel

@Composable
fun ReviewRabbitApp(
    navController: NavHostController
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val companyReviewViewModel: CompanyReviewViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = ScreenRoutes.HOME) {
        composable(route = ScreenRoutes.HOME) {
            HomeScreen(
                homeScreenViewModel,
                onClick = { companyId ->
                    navController.navigate(ScreenRoutes.companyPageWithArg(companyId))
                }
            )
        }
        composable(route = ScreenRoutes.CREATE_NEW_COMPANY) {
            CreateCompanyScreen()
        }
        composable(route = ScreenRoutes.USER_SETTINGS) {
            UserSettingsScreen()
        }
        composable(
            route = ScreenRoutes.COMPANY_PAGE,
            arguments = listOf(navArgument("companyId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 500, delayMillis = 0)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 500)
                )
            },
        ) { backStackEntry ->
            val companyId = backStackEntry.arguments?.getInt("companyId")
            if (companyId != null) {
                CompanyPage(
                    companyReviewViewModel = companyReviewViewModel,
                    companyId = companyId
                )
            }
        }
    }
}