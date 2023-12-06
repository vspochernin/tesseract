package ru.tesseract.assets.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.tesseract.R
import ru.tesseract.assets.domain.DetailedAssetInfo
import ru.tesseract.diversifications.ui.RiskLevel
import ru.tesseract.ui.FavoriteIcon

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun AssetScreen(
    assetId: Int,
    navigator: DestinationsNavigator,
    viewModel: AssetViewModel = koinViewModel { parametersOf(assetId) },
) {
    val asset = viewModel.asset()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.asset_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = { FavoriteButton(asset) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        if (asset != null) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(padding)
                    .padding(vertical = 16.dp),
            ) {
                AssetDescription(title = asset.generalInfo.title, description = asset.description)
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                Price(
                    price = formatPrice(asset.generalInfo.price),
                    priceDiff = asset.generalInfo.annotatedPriceDiff(),
                )
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                RiskLevel(asset.riskLevel)
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                CompanyDescription(
                    title = asset.generalInfo.companyTitle,
                    description = asset.companyDescription,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun FavoriteButton(asset: DetailedAssetInfo?) {
    if (asset != null) {
        val favoriteButtonViewModel: AssetFavoriteButtonViewModel =
            koinViewModel { parametersOf(asset.generalInfo) }
        IconButton(onClick = { favoriteButtonViewModel.onClickFavorite() }) {
            FavoriteIcon(isFavorite = favoriteButtonViewModel.isFavorite)
        }
    }
}

@Composable
private fun CompanyDescription(title: String, description: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.asset_screen_organization),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(8.dp))
        Text(description)
    }
}

@Composable
private fun Price(price: String, priceDiff: AnnotatedString) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.asset_screen_price),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(price, style = MaterialTheme.typography.displayMedium)
        Text(priceDiff, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun AssetDescription(title: String, description: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.size(8.dp))
        Text(description)
    }
}
