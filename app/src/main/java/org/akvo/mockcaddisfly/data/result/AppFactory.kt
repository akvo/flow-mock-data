package org.akvo.mockcaddisfly.data.result

import org.akvo.mockcaddisfly.BuildConfig

import java.util.Locale

object AppFactory {
    fun createDefaultApp(): App {
        return App(BuildConfig.VERSION_NAME, Locale.getDefault().language)
    }
}
