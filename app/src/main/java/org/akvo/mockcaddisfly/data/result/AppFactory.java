package org.akvo.mockcaddisfly.data.result;

import org.akvo.mockcaddisfly.BuildConfig;

import java.util.Locale;

public class AppFactory {
    public static App createDefaultApp() {
        return new App(BuildConfig.VERSION_NAME, Locale.getDefault().getLanguage());
    }
}
