package ru.tesseract

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

enum class LoginMethod(val allowChangingPassword: Boolean) {
    Tesseract(allowChangingPassword = true),
    Google(allowChangingPassword = false),
}

@OptIn(DelicateCoroutinesApi::class)
@KoverIgnore
@Single
class LoginState(
    private val dataStore: DataStore<Preferences>,
) {
    private val tokenKey = stringPreferencesKey("token")
    private val loginMethodKey = intPreferencesKey("login_method")
    val token = dataStore.data.map { it[tokenKey] }
        .stateInBlocking(GlobalScope)
    val loginMethod = dataStore.data.map { data ->
        val ordinal = data[loginMethodKey]
        ordinal?.let { LoginMethod.entries[it] }
    }

    suspend fun resetToken() {
        dataStore.edit { it.remove(tokenKey) }
    }

    suspend fun setToken(token: String, method: LoginMethod) {
        dataStore.edit {
            it[tokenKey] = token
            it[loginMethodKey] = method.ordinal
        }
    }
}
