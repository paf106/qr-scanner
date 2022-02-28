package com.pablo.qrscanner.ui.view.components.utils

import android.content.Context

open class Prefs(context: Context) {

    val SHARED_NAME = "database"
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    val SHARED_CLIPBOARD = "clipboard"
    val SHARED_SOUND = "sound"
    val SHARED_OPEN_WEB = "openWeb"
    val SHARED_NATIVE_BROWSER = "nativeBrowser"

    fun setClipboard(valor: Boolean) {
        storage.edit().putBoolean(SHARED_CLIPBOARD, valor).apply()
    }

    fun isClipboardEnabled(): Boolean = storage.getBoolean(SHARED_CLIPBOARD, false)


    fun setSoundToScan(valor: Boolean) {
        storage.edit().putBoolean(SHARED_SOUND, valor).apply()
    }

    fun isSoundToScanEnabled(): Boolean = storage.getBoolean(SHARED_SOUND, false)


    fun setOpenWeb(valor: Boolean) {
        storage.edit().putBoolean(SHARED_OPEN_WEB, valor).apply()
    }

    fun isOpenWebEnabled(): Boolean = storage.getBoolean(SHARED_OPEN_WEB, false)

    fun setNativeBrowser(valor: Boolean) {
        storage.edit().putBoolean(SHARED_NATIVE_BROWSER, valor).apply()
    }

    fun isNativeBrowserEnabled(): Boolean = storage.getBoolean(SHARED_NATIVE_BROWSER, false)

}