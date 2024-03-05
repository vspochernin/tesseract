package ru.tesseract.portfolios.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.destinations.PortfolioScreenDestination
import ru.tesseract.destinations.NewPortfolioScreenDestination
import ru.tesseract.ui.loadingStates

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun PortfoliosScreen(
    navigator: DestinationsNavigator,
    viewModel: PortfoliosViewModel = koinViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.navigation_portfolios)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        val portfolios = viewModel.portfolios.collectAsLazyPagingItems()
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = { navigator.navigate(NewPortfolioScreenDestination) },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                            .testTag("PortfoliosScreen.CreateButton"),
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = stringResource(id = R.string.portfolios_screen_create))
                    }
                }
            }
            items(portfolios.itemCount, key = { it }) { i ->
                val portfolio = portfolios[i]
                if (portfolio != null) {
                    PortfolioSummary(
                        portfolio = portfolio,
                        onClick = {
                            val destination = PortfolioScreenDestination(portfolio.id)
                            navigator.navigate(destination)
                        },
                        modifier = Modifier.fillMaxWidth().testTag("PortfoliosScreen.Portfolio"),
                    )
                }
            }
            loadingStates(portfolios)
        }
    }
}
