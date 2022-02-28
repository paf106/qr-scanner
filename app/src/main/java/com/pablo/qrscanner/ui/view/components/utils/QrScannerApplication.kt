package com.pablo.qrscanner.ui.view.components.utils

import android.app.Application

class QrScannerApplication : Application() {
    companion object {
        lateinit var prefs: Prefs
    }


    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(this)
    }
}