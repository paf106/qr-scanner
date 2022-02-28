package com.pablo.qrscanner.data.model

import com.pablo.qrscanner.ui.view.history.ScanType

data class History(val idHistory: Int, val text: String, val timestamp: String, val scanType: Enum<ScanType>) {

}