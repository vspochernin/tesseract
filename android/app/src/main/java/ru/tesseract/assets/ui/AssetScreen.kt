package ru.tesseract.assets.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.tesseract.R
import ru.tesseract.assets.domain.Asset
import ru.tesseract.ui.FavoriteIcon

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun AssetScreen(
    assetId: Long,
    navigator: DestinationsNavigator,
) {
    val asset = sampleAssets.first { it.id == assetId }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.asset_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        FavoriteIcon(isFavorite = asset.isFavorite)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(padding)
                    .padding(16.dp),
        ) {
            AssetDescription(asset)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Price(asset)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            OrganizationDescription(asset)
        }
    }
}

@Composable
private fun OrganizationDescription(asset: Asset) {
    Text(
        text = stringResource(id = R.string.asset_screen_organization),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    Text(text = asset.organization, style = MaterialTheme.typography.titleLarge)
    Text(text = "Описание организации...")
}

@Composable
private fun Price(asset: Asset) {
    Text(
        text = stringResource(id = R.string.asset_screen_price),
        style = MaterialTheme.typography.labelLarge,
    )
    Text(text = asset.price, style = MaterialTheme.typography.displayMedium)
}

@Composable
private fun AssetDescription(asset: Asset) {
    Text(text = asset.name, style = MaterialTheme.typography.headlineMedium)
    Text(text = "Описание актива...")
}
