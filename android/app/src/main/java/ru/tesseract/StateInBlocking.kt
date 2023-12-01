package ru.tesseract

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

fun <T> Flow<T>.stateInBlocking(
    scope: CoroutineScope,
): StateFlow<T> = stateIn(scope, SharingStarted.Eagerly, runBlocking { first() })
