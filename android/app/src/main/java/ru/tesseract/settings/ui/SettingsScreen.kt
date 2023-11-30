package ru.tesseract.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.ui.theme.ThemeSetting

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.navigation_settings)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        Column(
            modifier =
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            ThemeItem(
                value = viewModel.state.themeSetting,
                setValue = viewModel::onSetTheme,
            )
            HeadingItem(stringResource(id = R.string.settings_screen_account))
            ChangePasswordItem()
            LogOutItem(onClick = viewModel::onLogOut)
        }
    }
}

@Composable
private fun HeadingItem(text: String) {
    Divider(color = MaterialTheme.colorScheme.outlineVariant)
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(16.dp),
    )
}

@Composable
private fun ChangePasswordItem() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val textFieldModifier = Modifier.fillMaxWidth()
        Text(
            stringResource(id = R.string.settings_screen_change_password),
            style = MaterialTheme.typography.titleMedium,
        )
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.settings_screen_old_password)) },
            value = "",
            onValueChange = {},
            modifier = textFieldModifier,
        )
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.settings_screen_new_password)) },
            value = "",
            onValueChange = {},
            modifier = textFieldModifier,
        )
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.settings_screen_confirm_new_password)) },
            value = "",
            onValueChange = {},
            modifier = textFieldModifier,
        )
        Button(onClick = { /*TODO*/ }, enabled = false, modifier = Modifier.align(Alignment.End)) {
            Text(stringResource(id = R.string.settings_screen_change_password_button))
        }
    }
}

@Composable
private fun LogOutItem(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth(),
    ) {
        Text(
            stringResource(id = R.string.settings_screen_log_out),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun ThemeItem(
    value: ThemeSetting,
    setValue: (ThemeSetting) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ThemeDialog(setValue = setValue, dismiss = { showDialog = false })
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { showDialog = true }
            .defaultMinSize(minHeight = 48.dp),
    ) {
        Column(
            modifier =
            Modifier
                .weight(1f)
                .padding(16.dp),
        ) {
            Text(
                stringResource(id = R.string.settings_screen_theme),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                stringResource(id = value.displayName),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeDialog(
    setValue: (ThemeSetting) -> Unit,
    dismiss: () -> Unit,
) {
    AlertDialog(onDismissRequest = { dismiss() }) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.large
        ) {
            Column {
                ThemeSetting.entries.forEach { setting ->
                    ListItem(
                        headlineContent = {
                            Text(stringResource(id = setting.displayName))
                        },
                        modifier = Modifier.clickable {
                            setValue(setting)
                            dismiss()
                        },
                    )
                }
            }
        }
    }
}
