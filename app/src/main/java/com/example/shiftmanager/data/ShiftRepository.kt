package com.example.shiftmanager.data

import com.example.shiftmanager.domain.Shift
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ShiftRepository(private val shiftDao: ShiftDao) {
    fun getAllShifts(): Flow<List<Shift>> {
        return shiftDao.getAllShifts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getShiftsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Shift>> {
        return shiftDao.getShiftsByDateRange(
            startDate.toString(),
            endDate.toString()
        ).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getShiftsByDate(date: LocalDate): Flow<List<Shift>> {
        return shiftDao.getShiftsByDate(date.toString()).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun insertShift(shift: Shift): Long {
        return shiftDao.insert(ShiftEntity.fromDomain(shift))
    }

    suspend fun updateShift(shift: Shift) {
        shiftDao.update(ShiftEntity.fromDomain(shift))
    }

    suspend fun deleteShift(shift: Shift) {
        shiftDao.delete(ShiftEntity.fromDomain(shift))
    }

    suspend fun deleteShiftById(id: Long) {
        shiftDao.deleteShiftById(id)
    }

    suspend fun deleteAllShifts() {
        shiftDao.deleteAll()
    }
}
