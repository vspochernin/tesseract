package ru.tesseract.assets.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sebaslogen.resaca.viewModelScoped
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import ru.tesseract.R
import ru.tesseract.assets.domain.GeneralAssetInfo
import ru.tesseract.diversifications.domain.DiversificationAsset
import ru.tesseract.ui.FavoriteIcon

@Composable
fun DiversificationAssetSummary(
    asset: DiversificationAsset,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssetSummary(
        key = asset,
        id = asset.id,
        title = asset.title,
        companyTitle = asset.companyTitle,
        price = asset.oldPrice,
        isFavorite = asset.isFavorite,
        additionalInfo = {
            Text(
                text = stringResource(id = R.string.asset_quantity, asset.count),
                style = MaterialTheme.typography.titleSmall,
            )
        },
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AssetSummaryWithChange(
    asset: GeneralAssetInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssetSummary(
        key = asset,
        id = asset.id,
        title = asset.title,
        companyTitle = asset.companyTitle,
        price = asset.price,
        isFavorite = asset.isFavorite,
        additionalInfo = {
            Text(asset.annotatedPriceDiff(), style = MaterialTheme.typography.titleMedium)
        },
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun AssetSummary(
    key: Any,
    id: Int,
    title: String,
    companyTitle: String,
    price: Int,
    isFavorite: Boolean,
    additionalInfo: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val koin = getKoin()
    val viewModel: AssetFavoriteButtonViewModel = viewModelScoped(key = key) {
        koin.get { parametersOf(id, isFavorite) }
    }
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = { viewModel.onClickFavorite() }) {
                FavoriteIcon(isFavorite = viewModel.isFavorite)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    companyTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(formatPrice(price), style = MaterialTheme.typography.titleMedium)
                additionalInfo()
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 56.dp),
        )
    }
}
