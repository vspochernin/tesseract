package ru.tesseract

import android.app.Application
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

class TesseractApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val dataStore = PreferenceDataStoreFactory.create {
            preferencesDataStoreFile("settings")
        }
        val dataStoreModule = module(createdAtStart = true) {
            single { dataStore }
        }
        startKoin {
            modules(dataStoreModule)
            defaultModule()
        }
    }
}
