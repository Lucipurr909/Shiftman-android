package com.example.shiftmanager.domain

import java.time.LocalDate
import java.time.LocalTime

enum class ShiftType {
    DAY,
    NIGHT,
    EXTRA,
    OFF,
    VACATION
}

data class Shift(
    val id: Long = 0,
    val date: LocalDate,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val type: ShiftType,
    val notes: String = ""
)
