package learningprogramming.academy.reviewrabbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import learningprogramming.academy.reviewrabbit.ui.components.botnavbar.ReviewRabbitBottomNavBar
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.DropdownMenuOverlay
import learningprogramming.academy.reviewrabbit.ui.components.topappbar.ReviewRabbitTopAppBar
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewRabbitTheme(dynamicColor = false) {
                var isLoggedin by rememberSaveable { mutableStateOf(false) }
                var isExpanded by rememberSaveable { mutableStateOf(false) }
                val navController = rememberNavController()

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
                                navController = navController
                            )
                            DropdownMenuOverlay(
                                isExpanded = isExpanded,
                                isLoggedIn = isLoggedin,
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ReviewRabbitTheme(dynamicColor = false, darkTheme = false) {
        var isLoggedin by rememberSaveable { mutableStateOf(false) }
        var isExpanded by rememberSaveable { mutableStateOf(true) }
        val navController = rememberNavController()
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
                    navController = navController
                )
                DropdownMenuOverlay(
                    isExpanded = isExpanded,
                    isLoggedIn = isLoggedin,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}