package ru.tesseract.portfolios.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toJavaInstant
import ru.tesseract.assets.ui.formatPrice
import ru.tesseract.portfolios.domain.Portfolio
import ru.tesseract.portfolios.domain.riskLevel
import ru.tesseract.ui.DefaultDateTimeFormatter
import ru.tesseract.ui.asAnnotatedPriceDiff

@Composable
fun PortfolioSummary(
    portfolio: Portfolio,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    DefaultDateTimeFormatter.format(portfolio.at.toJavaInstant()),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    stringResource(id = portfolio.riskLevel.explicitResId),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    formatPrice(portfolio.currentAmount),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    portfolio.amountDiff.asAnnotatedPriceDiff(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}
