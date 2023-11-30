package ru.tesseract.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import ru.tesseract.stateInBlocking
import ru.tesseract.ui.theme.ThemeSetting

private val ThemeKey = intPreferencesKey("theme")

@OptIn(DelicateCoroutinesApi::class)
@Single
class Settings(
    private val dataStore: DataStore<Preferences>,
) {
    val themeSetting = dataStore.data.map {
        val ordinal = it[ThemeKey] ?: 0
        ThemeSetting.entries[ordinal]
    }.stateInBlocking(GlobalScope)

    suspend fun setTheme(value: ThemeSetting) {
        dataStore.edit { it[ThemeKey] = value.ordinal }
    }
}
