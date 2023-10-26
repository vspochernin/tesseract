package ru.tesseract.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
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

@LoginNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = koinViewModel(),
) {
    Scaffold { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            LoginForm(
                onLogin = viewModel::onLogin,
                onRegister = { navigator.navigate(RegistrationScreenDestination) },
                modifier =
                    Modifier
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
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.tesseract),
            contentDescription = stringResource(id = R.string.app_name),
            modifier =
                Modifier
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
        GoogleSignInButton(
            onTokenReceived = { onLogin() },
            onDialogDismissed = { TODO() },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Box(
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            Divider(modifier = Modifier.align(Alignment.Center))
            Text(
                stringResource(id = R.string.login_screen_or_with_login),
                style = MaterialTheme.typography.labelMedium,
                modifier =
                    Modifier
                        .align(Alignment.Center)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp),
            )
        }
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.login_username_field)) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.login_password_field)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
        ) {
            Text(text = stringResource(id = R.string.login_screen_login_button))
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
