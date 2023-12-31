package ru.tesseract.diversifications.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.tesseract.R
import ru.tesseract.assets.ui.DiversificationAssetSummary
import ru.tesseract.assets.ui.formatPrice
import ru.tesseract.destinations.AssetScreenDestination
import ru.tesseract.diversifications.domain.DiversificationAsset
import ru.tesseract.diversifications.domain.RiskLevel
import ru.tesseract.diversifications.domain.riskLevel
import ru.tesseract.ui.DefaultDateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun DiversificationScreen(
    diversificationId: Int,
    navigator: DestinationsNavigator,
    viewModel: DiversificationViewModel = koinViewModel { parametersOf(diversificationId) },
) {
    val diversification = viewModel.diversification()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.diversification_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        if (diversification != null) {
            Column(
                modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(padding)
                    .padding(vertical = 16.dp),
            ) {
                Date(diversification.at)
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                RiskLevel(diversification.riskLevel)
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                DiversificationAmount(formatPrice(diversification.amount))
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                DiversificationAssets(diversification.assets, navigator)
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
private fun Date(date: Instant) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.diversification_date),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = DefaultDateTimeFormatter.format(date.toJavaInstant()),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun DiversificationAssets(
    assets: List<DiversificationAsset>,
    navigator: DestinationsNavigator
) {
    Text(
        text = stringResource(id = R.string.diversification_assets),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    assets.forEach { asset ->
        DiversificationAssetSummary(
            asset = asset,
            onClick = { navigator.navigate(AssetScreenDestination(asset.id)) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DiversificationAmount(value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.diversification_sum),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(text = value, style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun RiskLevel(riskLevel: RiskLevel) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.risk_tolerance),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = stringResource(id = riskLevel.resId),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
