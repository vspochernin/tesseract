package ru.tesseract.diversifications.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.tesseract.assets.ui.AssetSummary
import ru.tesseract.assets.ui.sampleAssets
import ru.tesseract.destinations.AssetScreenDestination
import ru.tesseract.diversifications.domain.RiskTolerance
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun DiversificationScreen(
    diversificationId: Long,
    navigator: DestinationsNavigator,
) {
    val diversification = sampleDiversifications.first { it.id == diversificationId }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.diversification_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
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
                    .padding(vertical = 16.dp),
        ) {
            Date(diversification.date)
            Divider(modifier = Modifier.padding(16.dp))
            RiskTolerance(diversification.riskTolerance)
            Divider(modifier = Modifier.padding(16.dp))
            DiversificationSum(diversification.sum)
            Divider(modifier = Modifier.padding(16.dp))
            DiversificationAssets(navigator)
        }
    }
}

@Composable
private fun Date(date: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.diversification_date),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(text = date, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun DiversificationAssets(navigator: DestinationsNavigator) {
    Text(
        text = stringResource(id = R.string.diversification_assets),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    sampleAssets.shuffled().take(5).forEach { asset ->
        AssetSummary(
            asset = asset,
            quantity = Random.nextInt(1, 6),
            onClick = { navigator.navigate(AssetScreenDestination(asset.id)) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DiversificationSum(sum: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.diversification_sum),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(text = sum, style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
private fun RiskTolerance(riskTolerance: RiskTolerance) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.risk_tolerance),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = stringResource(id = riskTolerance.resId),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}