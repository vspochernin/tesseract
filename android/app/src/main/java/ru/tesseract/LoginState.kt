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

private val TokenKey = stringPreferencesKey("token")
private val LoginMethodKey = intPreferencesKey("login_method")

enum class LoginMethod(val allowChangingPassword: Boolean) {
    Tesseract(allowChangingPassword = true),
    Google(allowChangingPassword = false),
}

@OptIn(DelicateCoroutinesApi::class)
@Single
class LoginState(
    private val dataStore: DataStore<Preferences>,
) {
    val token = dataStore.data.map { it[TokenKey] }
        .stateInBlocking(GlobalScope)
    val loginMethod = dataStore.data.map { data ->
        val ordinal = data[LoginMethodKey]
        ordinal?.let { LoginMethod.entries[it] }
    }

    suspend fun resetToken() {
        dataStore.edit { it.remove(TokenKey) }
    }

    suspend fun setToken(token: String, method: LoginMethod) {
        dataStore.edit {
            it[TokenKey] = token
            it[LoginMethodKey] = method.ordinal
        }
    }
}
