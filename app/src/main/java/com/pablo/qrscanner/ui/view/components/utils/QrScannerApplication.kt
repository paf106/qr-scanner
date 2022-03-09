package com.pablo.qrscanner.ui.view.components.utils

import android.app.Application

class QrScannerApplication : Application() {
    companion object {
        lateinit var prefs: Prefs
        const val PERMISO_DISCO = 1
        const val PERMISO_LLAMADA = 2
    }


    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(this)
    }
}