package ru.tesseract.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.tesseract.R
import ru.tesseract.ui.navigation.LoginNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@LoginNavGraph
@Destination
@Composable
fun RegistrationScreen(navigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.registration_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            RegisterForm(
                modifier =
                    Modifier
                        .widthIn(max = 360.dp)
                        .padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun RegisterForm(modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val textFieldModifier = Modifier.fillMaxWidth()
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.login_username_field)) },
            modifier = textFieldModifier,
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.registration_screen_email_field)) },
            modifier = textFieldModifier,
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.login_password_field)) },
            modifier = textFieldModifier,
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(id = R.string.registration_screen_confirm_password_field)) },
            modifier = textFieldModifier,
        )
        Button(
            onClick = { TODO() },
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.registration_screen_register_button))
        }
    }
}
