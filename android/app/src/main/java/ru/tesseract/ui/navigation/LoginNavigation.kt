package ru.tesseract.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import ru.tesseract.NavGraphs

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun LoginNavigation() {
    val engine =
        rememberAnimatedNavHostEngine(
            rootDefaultAnimations = MaterialTransitions.rootDefaultAnimations,
        )
    DestinationsNavHost(navGraph = NavGraphs.login, engine = engine)
}
