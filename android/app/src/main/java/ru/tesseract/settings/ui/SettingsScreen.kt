package ru.tesseract.settings.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
            val state = viewModel.state
            ThemeItem(
                value = state.themeSetting,
                setValue = viewModel::onSetTheme,
            )
            HeadingItem(stringResource(id = R.string.settings_screen_account))
            ChangePasswordItem(state.allowChangingPassword)
            LogOutItem(onClick = viewModel::onLogOut)
        }
    }
}

@Composable
private fun HeadingItem(text: String) {
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(16.dp),
    )
}

@Composable
private fun ChangePasswordItem(
    allowChangingPassword: Boolean,
    viewModel: ChangePasswordViewModel = koinViewModel()
) {
    if (viewModel.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showSuccessDialog = false },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.showSuccessDialog = false },
                    modifier = Modifier.testTag("SettingsScreen.DismissDialogButton"),
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            text = { Text(text = stringResource(id = R.string.settings_screen_password_changed)) },
            modifier = Modifier.testTag("SettingsScreen.SuccessDialog"),
        )
    }
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val focusManager = LocalFocusManager.current
        val textFieldModifier = Modifier.fillMaxWidth()
        Text(
            stringResource(id = R.string.settings_screen_change_password),
            style = MaterialTheme.typography.titleMedium,
        )
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.settings_screen_old_password)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
            visualTransformation = PasswordVisualTransformation(),
            value = viewModel.oldPassword,
            onValueChange = { viewModel.oldPassword = it },
            modifier = textFieldModifier.testTag("SettingsScreen.OldPassword"),
        )
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text(stringResource(id = R.string.settings_screen_new_password)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = textFieldModifier.testTag("SettingsScreen.NewPassword"),
            isError = viewModel.displayPasswordError,
        )
        AnimatedVisibility(visible = viewModel.displayPasswordError) {
            Text(stringResource(id = R.string.error_incorrect_password), color = MaterialTheme.colorScheme.error)
        }
        OutlinedTextField(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            label = { Text(stringResource(id = R.string.registration_screen_confirm_password_field)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false),
            visualTransformation = PasswordVisualTransformation(),
            modifier = textFieldModifier.testTag("SettingsScreen.ConfirmPassword"),
            isError = viewModel.displayConfirmPasswordError,
        )
        AnimatedVisibility(visible = viewModel.displayConfirmPasswordError) {
            Text(stringResource(id = R.string.validation_incorrect_confirm_password), color = MaterialTheme.colorScheme.error)
        }
        val enabled = allowChangingPassword && viewModel.isButtonEnabled
        Button(
            onClick = { viewModel.onChange() },
            enabled = enabled,
            modifier = Modifier
                .align(Alignment.End)
                .let {
                    if (enabled) {
                        it.testTag("SettingsScreen.ConfirmPasswordChange")
                    } else {
                        it
                    }
                },
        ) {
            Text(stringResource(id = R.string.settings_screen_change_password_button))
        }
    }
}

@Composable
private fun LogOutItem(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth()
            .testTag("SettingsScreen.LogOutButton"),
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
            modifier = Modifier
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
    BasicAlertDialog(onDismissRequest = { dismiss() }) {
        Surface(shape = MaterialTheme.shapes.large) {
            Column {
                ThemeSetting.entries.forEach { setting ->
                    ListItem(
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
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
