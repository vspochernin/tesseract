package ru.tesseract.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState
import ru.tesseract.KoverIgnore
import ru.tesseract.NavGraphs
import ru.tesseract.R
import ru.tesseract.destinations.AssetsScreenDestination
import ru.tesseract.destinations.DirectionDestination
import ru.tesseract.destinations.DiversificationsScreenDestination
import ru.tesseract.destinations.FavoritesScreenDestination
import ru.tesseract.destinations.SettingsScreenDestination

@KoverIgnore
enum class MainNavigationBarItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    Assets(
        AssetsScreenDestination,
        Icons.AutoMirrored.Filled.TrendingUp,
        R.string.navigation_assets,
    ),
    Favorites(FavoritesScreenDestination, Icons.Default.Favorite, R.string.navigation_favorites),
    Diversifications(
        DiversificationsScreenDestination,
        Icons.Default.PieChart,
        R.string.navigation_diversifications,
    ),
    Settings(SettingsScreenDestination, Icons.Default.Settings, R.string.navigation_settings),
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MainNavigationBar(navController = navController) },
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        val engine =
            rememberAnimatedNavHostEngine(
                rootDefaultAnimations = MaterialTransitions.rootDefaultAnimations,
            )
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            engine = engine,
            navController = navController,
            startRoute = NavGraphs.root.startRoute,
            modifier = Modifier.padding(padding),
        )
    }
}

@Composable
private fun MainNavigationBar(navController: NavHostController) {
    NavigationBar {
        MainNavigationBarItem.entries.forEach { item ->
            val isOnBackStack by navController.isRouteOnBackStackAsState(route = item.direction)
            NavigationBarItem(
                selected = isOnBackStack,
                onClick = {
                    if (isOnBackStack) {
                        navController.popBackStack(item.direction, false)
                    } else {
                        navController.navigate(item.direction) {
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.label),
                    )
                },
                label = {
                    Text(stringResource(id = item.label), fontSize = 9.sp)
                },
            )
        }
    }
}
