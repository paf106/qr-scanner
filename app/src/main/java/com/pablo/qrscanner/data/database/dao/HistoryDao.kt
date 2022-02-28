package com.pablo.qrscanner.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pablo.qrscanner.data.database.entities.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history_table")
    fun getAll(): LiveData<List<HistoryEntity>>

    @Query("DELETE FROM history_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryEntity)

    @Delete
    suspend fun delete(history: HistoryEntity)
}