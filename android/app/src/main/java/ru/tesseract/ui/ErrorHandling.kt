package ru.tesseract.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.R
import ru.tesseract.api.ApiErrorType

@Composable
fun ErrorHandling(viewModel: ErrorHandlingViewModel = koinViewModel()) {
    val context = LocalContext.current
    var error by remember { mutableStateOf<ApiErrorType?>(null) }
    LaunchedEffect(Unit) {
        viewModel.apiErrors.collect {
            error = it
        }
    }
    LaunchedEffect(Unit) {
        viewModel.networkErrors.collect {
            Log.d("Error Handling", "Network Error", it.exception)
            Toast.makeText(context, R.string.error_network_error, Toast.LENGTH_SHORT).show()
        }
    }
    error?.let {
        AlertDialog(
            onDismissRequest = { error = null },
            text = {
                Text(text = stringResource(id = it.message))
            },
            confirmButton = {
                TextButton(onClick = { error = null }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            modifier = Modifier.testTag("error_dialog")
        )
    }
}
