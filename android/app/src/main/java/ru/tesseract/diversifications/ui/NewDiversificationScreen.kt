package ru.tesseract.diversifications.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.diversifications.domain.RiskLevel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun NewDiversificationScreen(
    navigator: DestinationsNavigator,
    viewModel: NewDiversificationViewModel = koinViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.new_diversification_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(padding)
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = viewModel.amountField,
                onValueChange = {
                    viewModel.amountField = it
                    viewModel.allowError = true
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = viewModel.showAmountError,
                suffix = { Text(text = "₽") },
                label = { Text(text = stringResource(id = R.string.diversification_sum)) },
                modifier = Modifier.fillMaxWidth(),
            )
            AnimatedVisibility(visible = viewModel.showAmountError) {
                Text(
                    text = stringResource(id = R.string.validation_invalid_amount),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Text(
                text = stringResource(id = R.string.risk_tolerance),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )
            Column(modifier = Modifier.selectableGroup()) {
                RiskLevel.entries.forEach { riskLevel ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .selectable(
                                selected = false,
                                onClick = { viewModel.riskLevel = riskLevel },
                                role = Role.RadioButton,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = viewModel.riskLevel == riskLevel, onClick = null)
                        Text(
                            text = stringResource(id = riskLevel.resId),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
            Button(
                onClick = {
                    viewModel.onCreate {
                        navigator.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                enabled = viewModel.isButtonEnabled,
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                } else {
                    Text(stringResource(id = R.string.new_diversification_screen_create))
                }
            }
        }
    }
}
