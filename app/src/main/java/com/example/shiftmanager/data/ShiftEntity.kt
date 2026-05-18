package com.example.shiftmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shiftmanager.domain.Shift
import com.example.shiftmanager.domain.ShiftType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // ISO format: YYYY-MM-DD
    val startTime: String?, // HH:mm format
    val endTime: String?, // HH:mm format
    val type: String, // Enum name
    val notes: String = ""
) {
    fun toDomain(): Shift {
        return Shift(
            id = id,
            date = LocalDate.parse(date),
            startTime = startTime?.let { LocalTime.parse(it) },
            endTime = endTime?.let { LocalTime.parse(it) },
            type = ShiftType.valueOf(type),
            notes = notes
        )
    }

    companion object {
        fun fromDomain(shift: Shift): ShiftEntity {
            return ShiftEntity(
                id = shift.id,
                date = shift.date.toString(),
                startTime = shift.startTime?.toString(),
                endTime = shift.endTime?.toString(),
                type = shift.type.name,
                notes = shift.notes
            )
        }
    }
}
