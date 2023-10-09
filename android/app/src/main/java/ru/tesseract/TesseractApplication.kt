package ru.tesseract

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class TesseractApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { defaultModule() }
    }
}
