package ru.tesseract.login.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import ru.tesseract.R

private const val ClientId =
    "813296813934-9dlvchpp3bnk8gt1h5seg19bkh5cn18d.apps.googleusercontent.com"

@Composable
fun GoogleSignInButton(
    onTokenReceived: (String) -> Unit,
    onFailure: (Exception) -> Unit,
    enabled: Boolean,
    modifier: Modifier,
) {
    val client = rememberSignInClient(
        onTokenReceived = onTokenReceived,
        onFailure = onFailure,
    )
    val coroutineScope = rememberCoroutineScope()
    ElevatedButton(
        onClick = { coroutineScope.launch { client.signIn() } },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
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

private fun interface SignInClient {
    suspend fun signIn()
}

@Composable
private fun rememberSignInClient(
    onTokenReceived: (String) -> Unit,
    onFailure: (Exception) -> Unit,
): SignInClient {
    val activity = LocalContext.current.findActivity()
    val credentialManager = remember { CredentialManager.create(activity) }
    return remember(activity) {
        SignInClient {
            val signInOption = GetSignInWithGoogleOption
                .Builder(ClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(signInOption)
                .build()

            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = activity,
                )
                val credential = result.credential
                if (credential !is CustomCredential || credential.type != TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    Log.e("Google OAuth", "Unexpected credential type")
                    return@SignInClient
                }
                try {
                    val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    onTokenReceived(googleCredential.idToken)
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("Google OAuth", "Received an invalid google id token response", e)
                    onFailure(e)
                }

            } catch (e: GetCredentialException) {
                Log.e("Google OAuth", "Request unsuccessful", e)
                onFailure(e)
            }
        }
    }
}

private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    error("Activity not found")
}
