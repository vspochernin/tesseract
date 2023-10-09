package ru.tesseract.assets.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.tesseract.R
import ru.tesseract.assets.domain.Asset
import ru.tesseract.destinations.AssetScreenDestination

val sampleAssets =
    listOf(
        Asset(
            id = 0,
            name = "АО «ЛИД» – Денежное требование №1",
            organization = "АО «ЛИД»",
            price = "220.33 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 1,
            name = "ООО «Фудсервис» – Денежное требование №1",
            organization = "ООО «Фудсервис»",
            price = "225.04 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 2,
            name = "ООО «Фудсервис» – Денежное требование №2",
            organization = "ООО «Фудсервис»",
            price = "211.89 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 3,
            name = "ИП Почернин Владислав Сергеевич – Денежное требование №1",
            organization = "ИП Почернин Владислав Сергеевич",
            price = "228.00 ₽",
            isFavorite = true,
        ),
        Asset(
            id = 4,
            name = "ООО «Новый Горизонт» – Денежное требование №1",
            organization = "ООО «Новый Горизонт»",
            price = "22.00 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 5,
            name = "ООО «Сетевые технологии» – Денежное требование №1",
            organization = "ООО «Сетевые технологии»",
            price = "137.00 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 6,
            name = "ООО «Сетевые технологии» – Денежное требование №2",
            organization = "ООО «Сетевые технологии»",
            price = "17.00 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 7,
            name = "ООО «Сетевые технологии» – Денежное требование №3",
            organization = "ООО «Сетевые технологии»",
            price = "13.00 ₽",
            isFavorite = false,
        ),
        Asset(
            id = 8,
            name = "ИП Мурзаканов Ислам Мухамадинович – Денежное требование №1",
            organization = "ИП Мурзаканов Ислам Мухамадинович",
            price = "2445.00 ₽",
            isFavorite = true,
        ),
        Asset(
            id = 9,
            name = "ИП Шиляев Владислав Сергеевич – Денежное требование №1",
            organization = "ИП Шиляев Владислав Сергеевич",
            price = "1212.00 ₽",
            isFavorite = true,
        ),
        Asset(
            id = 10,
            name = "ИП Разукрантов Владислав Евгеньевич – Денежное требование №1",
            organization = "ИП Разукрантов Владислав Евгеньевич",
            price = "2124.00 ₽",
            isFavorite = true,
        ),
    )

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun AssetsScreen(navigator: DestinationsNavigator) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.navigation_assets)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier =
                Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            items(sampleAssets) {
                AssetSummary(
                    asset = it,
                    quantity = null,
                    onClick = { navigator.navigate(AssetScreenDestination(it.id)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
