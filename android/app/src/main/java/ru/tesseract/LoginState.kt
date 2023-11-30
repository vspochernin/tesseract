package ru.tesseract

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single

private val TokenKey = stringPreferencesKey("token")

@OptIn(DelicateCoroutinesApi::class)
@Single
class LoginState(
    private val dataStore: DataStore<Preferences>,
) {
    private val tokenFlow = dataStore.data.map { it[TokenKey] }
    val token = tokenFlow.stateIn(GlobalScope, SharingStarted.Eagerly, runBlocking { tokenFlow.first() })

    suspend fun resetToken() {
        dataStore.edit { it.remove(TokenKey) }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { it[TokenKey] = token }
    }
}
