package learningprogramming.academy.reviewrabbit.ui.components.topappbar


import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AnimatedIcon(isExpanded: Boolean) {
    val transition = updateTransition(targetState = isExpanded, label = "Icon Transition")

    val rotation by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300, easing = LinearOutSlowInEasing) },
        label = "Rotation Animation"
    ) { expanded ->
        if (expanded) 90f else 0f
    }
    Icon(
        imageVector = if (isExpanded) Icons.Filled.Clear else Icons.Filled.Menu,
        contentDescription = "TopBar Menu",
        modifier = Modifier.graphicsLayer {
            rotationZ = rotation
        }
    )

}