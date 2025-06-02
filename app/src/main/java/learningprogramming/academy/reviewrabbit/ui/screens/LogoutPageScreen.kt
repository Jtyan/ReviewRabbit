package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme

@Composable
fun LogoutPageScreen(
    modifier: Modifier = Modifier,
    onLogoutSuccessNavigation: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000L)
        onLogoutSuccessNavigation()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Log out",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You have successfully logged out.",
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Redirecting back to Home...",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogoutPageScreenPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        LogoutPageScreen(
            onLogoutSuccessNavigation = {}
        )
    }
}