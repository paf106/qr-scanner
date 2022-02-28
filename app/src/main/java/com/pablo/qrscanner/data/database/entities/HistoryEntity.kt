package com.pablo.qrscanner.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pablo.qrscanner.ui.view.history.ScanType

@Entity(tableName = "history_table")
data class HistoryEntity(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "scanType") val scanType: ScanType,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idHistory") val idHistory: Int = 0
)