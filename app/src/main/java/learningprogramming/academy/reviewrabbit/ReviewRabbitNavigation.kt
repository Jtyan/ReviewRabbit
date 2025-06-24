package learningprogramming.academy.reviewrabbit

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import learningprogramming.academy.reviewrabbit.model.ScreenRoutes
import learningprogramming.academy.reviewrabbit.ui.screens.ChangePasswordScreen
import learningprogramming.academy.reviewrabbit.ui.screens.CompanyPage
import learningprogramming.academy.reviewrabbit.ui.screens.CreateCompanyScreen
import learningprogramming.academy.reviewrabbit.ui.screens.ForgotPasswordScreen
import learningprogramming.academy.reviewrabbit.ui.screens.HomeScreen
import learningprogramming.academy.reviewrabbit.ui.screens.LoginPageScreen
import learningprogramming.academy.reviewrabbit.ui.screens.LogoutPageScreen
import learningprogramming.academy.reviewrabbit.ui.screens.ProfileSettingsScreen
import learningprogramming.academy.reviewrabbit.ui.screens.ResetPasswordScreen
import learningprogramming.academy.reviewrabbit.ui.screens.SignupPageScreen
import learningprogramming.academy.reviewrabbit.viewmodels.ChangePasswordViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.CompanyReviewViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.ForgotPasswordViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.InviteViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.LoginViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.PostCompanyViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.ResetPasswordViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.SignupViewModel

@Composable
fun ReviewRabbitApp(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    companyReviewViewModel: CompanyReviewViewModel,
    loginViewModel: LoginViewModel,
    forgotPasswordViewModel: ForgotPasswordViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
    inviteViewModel: InviteViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    postCompanyViewModel: PostCompanyViewModel,
    signupViewModel: SignupViewModel
) {

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
            CreateCompanyScreen(
                postCompanyViewModel = postCompanyViewModel
            )
        }
        composable(route = ScreenRoutes.USER_SETTINGS) {
            ProfileSettingsScreen(
                inviteViewModel = inviteViewModel,
                onChangePasswordClick = { navController.navigate(ScreenRoutes.CHANGE_PASSWORD) }
            )
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
                    invitesViewModel = inviteViewModel,
                    companyReviewViewModel = companyReviewViewModel,
                    companyId = companyId
                )
            }
        }
        composable(route = ScreenRoutes.LOGIN_PAGE) {
            LoginPageScreen(
                loginViewModel = loginViewModel,
                onLoginSuccessNavigation = { navController.navigate(ScreenRoutes.HOME) },
                onForgotPasswordClick = { navController.navigate(ScreenRoutes.FORGOT_PASSWORD) }
            )
        }
        composable(route = ScreenRoutes.LOGOUT_PAGE) {
            LogoutPageScreen(
                onLogoutSuccessNavigation = {
                    navController.navigate(ScreenRoutes.HOME) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(route = ScreenRoutes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                forgotPasswordViewModel = forgotPasswordViewModel,
                onHaveTokenClick = { navController.navigate(ScreenRoutes.RESET_PASSWORD) }
            )
        }
        composable(route = ScreenRoutes.RESET_PASSWORD) {
            ResetPasswordScreen(
                resetPasswordViewModel = resetPasswordViewModel,
                onResetSuccessNavigation = { notification ->
                    navController.navigate(ScreenRoutes.loginPageWithArg(notification)) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                        }
                    }
                },
                onForgotPasswordClick = { navController.navigate(ScreenRoutes.FORGOT_PASSWORD) },
            )
        }
        composable(
            route = ScreenRoutes.LOGIN_PAGE_AFTER_RESET,
            arguments = listOf(navArgument("reset") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val message = navBackStackEntry.arguments?.getString("reset")
            LoginPageScreen(
                loginViewModel = loginViewModel,
                notification = message,
                onLoginSuccessNavigation = { navController.navigate(ScreenRoutes.HOME) },
                onForgotPasswordClick = { navController.navigate(ScreenRoutes.FORGOT_PASSWORD) }
            )
        }
        composable(route = ScreenRoutes.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                changePasswordViewModel = changePasswordViewModel,
                onPasswordChangeSuccessNavigation = { navController.navigate(ScreenRoutes.USER_SETTINGS) }
            )
        }
        composable(route = ScreenRoutes.SIGNUP_PAGE) {
            SignupPageScreen(
                signupViewModel = signupViewModel,
                onSignupSuccessNavigation = { navController.navigate(ScreenRoutes.LOGIN_PAGE) },
                onLoginClick = { navController.navigate(ScreenRoutes.LOGIN_PAGE) }
            )
        }
    }
}