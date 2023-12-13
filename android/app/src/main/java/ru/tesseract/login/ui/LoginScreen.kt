package ru.tesseract.login.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.destinations.InfoScreenDestination
import ru.tesseract.destinations.RegistrationScreenDestination
import ru.tesseract.ui.navigation.LoginNavGraph

@OptIn(ExperimentalLayoutApi::class)
@LoginNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = koinViewModel(),
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .imeNestedScroll(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            LoginForm(
                login = viewModel.login,
                password = viewModel.password,
                isSignInEnabled = viewModel.isSignInEnabled,
                isLoggingIn = viewModel.isLoggingIn,
                onLogin = viewModel::onLogin,
                onGoogleLogin = viewModel::onGoogleLogin,
                onRegister = { navigator.navigate(RegistrationScreenDestination) },
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { navigator.navigate(InfoScreenDestination) }) {
                Text(
                    stringResource(id = R.string.login_screen_information),
                )
            }
        }
    }
}

@Composable
private fun LoginForm(
    login: MutableState<String>,
    password: MutableState<String>,
    isSignInEnabled: Boolean,
    isLoggingIn: Boolean,
    onLogin: () -> Unit,
    onGoogleLogin: (String) -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.tesseract),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .heightIn(max = 120.dp)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = stringResource(id = R.string.login_screen_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.size(24.dp))
        val context = LocalContext.current
        GoogleSignInButton(
            onTokenReceived = { onGoogleLogin(it) },
            onFailure = {
                Toast.makeText(context, R.string.error_google_oauth, Toast.LENGTH_SHORT).show()
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Box(
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            HorizontalDivider(modifier = Modifier.align(Alignment.Center))
            Text(
                stringResource(id = R.string.login_screen_or_with_login),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp),
            )
        }
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = login.value,
            onValueChange = { login.value = it },
            label = { Text(stringResource(id = R.string.login_username_field)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(id = R.string.login_password_field)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false),
            keyboardActions = KeyboardActions { onLogin() },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            enabled = isSignInEnabled,
        ) {
            if (isLoggingIn) {
                CircularProgressIndicator(
                    Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            } else {
                Text(text = stringResource(id = R.string.login_screen_login_button))
            }
        }
        TextButton(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
        ) {
            Text(
                text =
                buildAnnotatedString {
                    append(stringResource(id = R.string.login_screen_dont_have_account))
                    append(" ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = R.string.login_screen_register_button))
                    }
                },
            )
        }
    }
}
