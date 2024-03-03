package ru.tesseract.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import ru.tesseract.stateInBlocking
import ru.tesseract.ui.theme.ThemeSetting

private val ThemeKey = intPreferencesKey("theme")
private val BaseUrlKey = stringPreferencesKey("base_url")
private const val DefaultBaseUrl = "http://10.0.2.2:8080/api/v1/"

@OptIn(DelicateCoroutinesApi::class)
@Single
class Settings(
    private val dataStore: DataStore<Preferences>,
) {
    val themeSetting = dataStore.data.map {
        val ordinal = it[ThemeKey] ?: 0
        ThemeSetting.entries[ordinal]
    }.stateInBlocking(GlobalScope)

    val baseUrl = dataStore.data
        .map { it[BaseUrlKey] ?: DefaultBaseUrl }
        .map { if (!it.endsWith('/')) "$it/" else it }
        .stateInBlocking(GlobalScope)

    suspend fun setTheme(value: ThemeSetting) {
        dataStore.edit { it[ThemeKey] = value.ordinal }
    }

    suspend fun setBaseUrl(value: String) {
        dataStore.edit { it[BaseUrlKey] = value }
    }
}
