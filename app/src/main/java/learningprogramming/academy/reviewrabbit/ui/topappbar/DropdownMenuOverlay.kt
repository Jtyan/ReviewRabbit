package learningprogramming.academy.reviewrabbit.ui.components.topappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuOverlay(
    isExpanded: Boolean,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = slideInVertically() + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        modifier = modifier
    ){
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(start = 30.dp, end = 30.dp, top = 10.dp )
            ) {
                HorizontalDivider(
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary)
                )
                if (isLoggedIn) {
                    MenuText(
                        text = "Logout",
                        onClick = {}
                    )
                } else if(!isLoggedIn) {
                    MenuText(
                        text = "Login",
                        onClick = {}
                    )
                    HorizontalDivider(
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary)
                    )
                    MenuText(
                        text = "Signup",
                        onClick = {}
                    )
                }
            }
        }
    }
}