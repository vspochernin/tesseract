package ru.tesseract.info.ui

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.tesseract.R
import ru.tesseract.ui.navigation.LoginNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@LoginNavGraph
@Destination
@Composable
fun InfoScreen(navigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.info_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val scale by rememberInfiniteTransition(label = "scale_tesseract_infinite").animateFloat(
                initialValue = 0.8f,
                targetValue = 1f,
                animationSpec = InfiniteRepeatableSpec(tween(1000), RepeatMode.Reverse),
                label = "scale_tesseract",
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.tesseract),
                contentDescription = stringResource(id = R.string.app_name),
                modifier =
                Modifier
                    .size(220.dp)
                    .scale(scale),
            )
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                stringResource(id = R.string.info_screen_text),
                modifier = Modifier.widthIn(max = 320.dp)
            )
        }
    }
}
