package ru.tesseract.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import com.ramcosta.composedestinations.animations.defaults.DestinationEnterTransition
import com.ramcosta.composedestinations.animations.defaults.DestinationExitTransition
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import ru.tesseract.KoverIgnore

@KoverIgnore
object MaterialTransitions {
    private val enter =
        DestinationEnterTransition {
            fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90))
        }

    private val exit =
        DestinationExitTransition {
            fadeOut(animationSpec = tween(90))
        }

    private val popEnter =
        DestinationEnterTransition {
            fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                scaleIn(
                    initialScale = 1.087f,
                    animationSpec = tween(220, delayMillis = 90),
                )
        }

    private val popExit =
        DestinationExitTransition {
            fadeOut(animationSpec = tween(90))
        }

    val rootDefaultAnimations =
        RootNavGraphDefaultAnimations(
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit,
        )
}
