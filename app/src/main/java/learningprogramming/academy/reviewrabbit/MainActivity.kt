package learningprogramming.academy.reviewrabbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import learningprogramming.academy.reviewrabbit.model.ScreenRoutes
import learningprogramming.academy.reviewrabbit.ui.components.botnavbar.ReviewRabbitBottomNavBar
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.DropdownMenuOverlay
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.ReviewRabbitTopAppBar
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.viewmodels.CompanyReviewViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.HomeScreenViewModel
import learningprogramming.academy.reviewrabbit.viewmodels.UserViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewRabbitTheme(dynamicColor = false) {
                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
                val companyReviewViewModel: CompanyReviewViewModel = hiltViewModel()
                val userViewModel: UserViewModel = hiltViewModel()

                val navController = rememberNavController()
                var isExpanded by rememberSaveable { mutableStateOf(false) }
                val isLoggedIn by userViewModel.isLoggedIn.collectAsState()

                Box {
                    Scaffold(
                        topBar = {
                            ReviewRabbitTopAppBar(
                                isExpanded = isExpanded,
                                toggleExpanded = { isExpanded = !isExpanded }
                            )
                        },
                        bottomBar = {
                            ReviewRabbitBottomNavBar(
                                navController = navController
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            ReviewRabbitApp(
                                navController = navController,
                                homeScreenViewModel = homeScreenViewModel,
                                companyReviewViewModel = companyReviewViewModel,
                                userViewModel = userViewModel
                            )
                            DropdownMenuOverlay(
                                isExpanded = isExpanded,
                                isLoggedIn = isLoggedIn,
                                onLogoutClick = {
                                    userViewModel.onLogoutClicked()
                                    navController.navigate(route = ScreenRoutes.LOGGOUT_PAGE)
                                    isExpanded = !isExpanded
                                },
                                onLoginClick = {
                                    navController.navigate(route = ScreenRoutes.LOGIN_PAGE)
                                    isExpanded = !isExpanded
                                },
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}
