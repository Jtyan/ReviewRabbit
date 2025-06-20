package learningprogramming.academy.reviewrabbit.ui.components.topappbar


import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AnimatedIcon(
    targetState: Boolean,
    iconIfTrue: ImageVector,
    iconIfFalse: ImageVector,
    contentDescription: String
) {
    val transition = updateTransition(targetState = targetState, label = "Icon Transition")

    val rotation by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300, easing = LinearOutSlowInEasing) },
        label = "Rotation Animation"
    ) { expanded ->
        if (expanded) 0f else -180f
    }
    Icon(
        imageVector = if (targetState) iconIfTrue else iconIfFalse,
        contentDescription = contentDescription,
        modifier = Modifier.graphicsLayer {
            rotationZ = rotation
        }
    )

}