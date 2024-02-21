package ru.tesseract

import android.app.Activity
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

inline fun <reified A : Activity> launchActivity(): ActivityScenario<A> {
    val intent = Intent(ApplicationProvider.getApplicationContext(), A::class.java)
    return ActivityScenario.launch(intent)
}
