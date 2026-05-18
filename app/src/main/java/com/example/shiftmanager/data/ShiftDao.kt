package com.example.shiftmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Insert
    suspend fun insert(shift: ShiftEntity): Long

    @Update
    suspend fun update(shift: ShiftEntity)

    @Delete
    suspend fun delete(shift: ShiftEntity)

    @Query("SELECT * FROM shifts WHERE id = :id")
    fun getShiftById(id: Long): Flow<ShiftEntity?>

    @Query("SELECT * FROM shifts ORDER BY date ASC")
    fun getAllShifts(): Flow<List<ShiftEntity>>

    @Query("SELECT * FROM shifts WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getShiftsByDateRange(startDate: String, endDate: String): Flow<List<ShiftEntity>>

    @Query("SELECT * FROM shifts WHERE date = :date ORDER BY startTime ASC")
    fun getShiftsByDate(date: String): Flow<List<ShiftEntity>>

    @Query("DELETE FROM shifts WHERE id = :id")
    suspend fun deleteShiftById(id: Long)

    @Query("DELETE FROM shifts")
    suspend fun deleteAll()
}
