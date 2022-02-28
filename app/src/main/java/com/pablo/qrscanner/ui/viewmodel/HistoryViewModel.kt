package com.pablo.qrscanner.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pablo.qrscanner.data.database.HistoryDatabase
import com.pablo.qrscanner.data.database.entities.HistoryEntity
import com.pablo.qrscanner.data.model.History
import com.pablo.qrscanner.ui.view.components.utils.WebViewActivity
import kotlinx.coroutines.withContext

class HistoryViewModel() : ViewModel() {

    val historyItems = MutableLiveData<List<HistoryEntity>>()




}