package org.akvo.mockcaddisfly.data.result;

import android.os.Build;

import java.util.Locale;

public class DeviceFactory {

    public static Device createDefaultDevice() {
        return new Device(Build.PRODUCT, "Android - " + Build.VERSION.RELEASE + " ("
                + Build.VERSION.SDK_INT + ")", Build.MODEL, Locale.getDefault().getLanguage(),
                Build.MANUFACTURER, Locale.getDefault().getCountry());
    }
}
