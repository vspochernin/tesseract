package ru.tesseract.assets.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.destinations.AssetScreenDestination
import ru.tesseract.ui.loadingStates

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun AssetsScreen(navigator: DestinationsNavigator, viewModel: AssetsViewModel = koinViewModel()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.navigation_assets)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        val assets = viewModel.getAll().collectAsLazyPagingItems()
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            items(assets.itemCount, key = { it }) { i ->
                val asset = assets[i]
                if (asset != null) {
                    AssetSummaryWithChange(
                        asset = asset,
                        onClick = { navigator.navigate(AssetScreenDestination(asset.id)) },
                        modifier = Modifier.fillMaxWidth().testTag("AssetsScreen.Asset"),
                    )
                }
            }
            loadingStates(assets)
        }
    }
}

