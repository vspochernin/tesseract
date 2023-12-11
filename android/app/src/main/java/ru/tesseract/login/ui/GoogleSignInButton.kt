package ru.tesseract.login.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import ru.tesseract.R

private const val ClientId =
    "813296813934-87d70t4effnrot1fj30hjbc63ddld1g2.apps.googleusercontent.com"

@Composable
fun GoogleSignInButton(
    onTokenReceived: (String) -> Unit,
    onDialogDismissed: () -> Unit,
    enabled: Boolean,
    modifier: Modifier,
) {
    val client = rememberSignInClient(
        onTokenReceived = onTokenReceived,
        onDialogDismissed = onDialogDismissed,
    )
    ElevatedButton(
        onClick = client::signIn,
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
    fun signIn()
}

@Composable
private fun rememberSignInClient(
    onTokenReceived: (String) -> Unit,
    onDialogDismissed: () -> Unit,
): SignInClient {
    val activity = LocalContext.current.findActivity()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                val client = Identity.getSignInClient(activity)
                val credentials = client.getSignInCredentialFromIntent(result.data)
                credentials.googleIdToken?.let { token ->
                    onTokenReceived(token)
                }
            } else {
                onDialogDismissed()
            }
        } catch (e: ApiException) {
            onDialogDismissed()
        }
    }
    return remember(activity) {
        SignInClient {
            val client = Identity.getSignInClient(activity)
            val request = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(ClientId)
                        .setFilterByAuthorizedAccounts(false)
                        .build(),
                )
                .setAutoSelectEnabled(true)
                .build()

            client.beginSignIn(request)
                .addOnSuccessListener { result ->
                    try {
                        val intentSenderRequest = IntentSenderRequest
                            .Builder(result.pendingIntent.intentSender).build()
                        launcher.launch(intentSenderRequest)
                    } catch (e: Exception) {
                        Log.d("Google OAuth", "Couldn't launch Google Sign In", e)
                        onDialogDismissed()
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("Google OAuth", "Couldn't launch Google Sign In", e)
                    onDialogDismissed()
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
