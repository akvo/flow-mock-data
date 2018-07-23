package org.akvo.mockcaddisfly.data.result

import android.os.Build

import java.util.Locale

object DeviceFactory {

    fun createDefaultDevice(): Device {
        return Device(Build.PRODUCT, "Android - " + Build.VERSION.RELEASE + " ("
                + Build.VERSION.SDK_INT + ")", Build.MODEL, Locale.getDefault().language,
                Build.MANUFACTURER, Locale.getDefault().country)
    }
}
