package learningprogramming.academy.reviewrabbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import learningprogramming.academy.reviewrabbit.ui.components.DropdownMenuOverlay
import learningprogramming.academy.reviewrabbit.ui.components.ReviewRabbitTopAppBar
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewRabbitTheme {
                var isLoggedin by rememberSaveable { mutableStateOf(false) }
                var isExpanded by rememberSaveable { mutableStateOf(false) }
                Box {
                    Scaffold(
                        topBar = {
                            ReviewRabbitTopAppBar(
                                isExpanded = isExpanded,
                                toggleExpanded = { isExpanded = !isExpanded }
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Box(
                            Modifier.fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            Greeting(
                                name = "Android",
                                modifier = Modifier
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


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ReviewRabbitTheme(dynamicColor = false, darkTheme = false) {
        var isLoggedin by rememberSaveable { mutableStateOf(false) }
        var isExpanded by rememberSaveable { mutableStateOf(true) }
        Box {
            Scaffold(
                topBar = {
                    ReviewRabbitTopAppBar(
                        isExpanded = isExpanded,
                        toggleExpanded = { isExpanded = !isExpanded }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    Modifier.fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Greeting(
                        name = "Android",
                        modifier = Modifier
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