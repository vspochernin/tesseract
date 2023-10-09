package ru.tesseract.login.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tesseract.R

@Composable
fun GoogleSignInButton(
    onTokenReceived: (String) -> Unit,
    onDialogDismissed: () -> Unit,
    enabled: Boolean,
    modifier: Modifier,
) {
    ElevatedButton(
        onClick = { onTokenReceived("") }, // TODO
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.DarkGray,
            ),
        modifier = modifier,
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            if (enabled) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                )
                Spacer(Modifier.width(16.dp))
                Text(stringResource(id = R.string.login_screen_google_button))
            } else {
                Text(stringResource(id = R.string.login_screen_signing_in))
            }
        }
    }
}
