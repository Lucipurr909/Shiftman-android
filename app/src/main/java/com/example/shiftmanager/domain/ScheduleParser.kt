package com.example.shiftmanager.domain

import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object ScheduleParser {

    fun parseSchedule(input: String, context: LocalDate = LocalDate.now()): List<Shift> {
        val trimmed = input.trim().lowercase()
        val shifts = mutableListOf<Shift>()

        return when {
            // Special keywords
            trimmed.contains("off") -> listOf(
                Shift(
                    date = context,
                    type = ShiftType.OFF,
                    notes = trimmed
                )
            )

            trimmed.contains("vacation") || trimmed.contains("holiday") -> listOf(
                Shift(
                    date = context,
                    type = ShiftType.VACATION,
                    notes = trimmed
                )
            )

            // ISO format: 2024-05-20: 14:30-22:30 or 2024-05-20 14:30-22:30
            trimmed.matches(Regex("\\d{4}-\\d{2}-\\d{2}[:\\s]+\\d{1,2}:\\d{2}[\\s]*-[\\s]*\\d{1,2}:\\d{2}")) -> {
                val datePart = trimmed.split(Regex("[:\\s]+"))[0]
                val timePart = trimmed.substringAfter(datePart).trim()
                    .replace(Regex("^[:\\s]+"), "")
                
                try {
                    val date = LocalDate.parse(datePart)
                    val times = timePart.split("-")
                    val startTime = LocalTime.parse(times[0].trim())
                    val endTime = LocalTime.parse(times[1].trim())
                    
                    listOf(
                        Shift(
                            date = date,
                            startTime = startTime,
                            endTime = endTime,
                            type = ShiftType.DAY,
                            notes = input
                        )
                    )
                } catch (e: Exception) {
                    emptyList()
                }
            }

            // Date number: 15th 09:00 to 17:00
            trimmed.matches(Regex("\\d{1,2}(?:st|nd|rd|th)?\\s+\\d{1,2}:\\d{2}\\s+to\\s+\\d{1,2}:\\d{2}")) -> {
                val dayMatch = Regex("\\d{1,2}").find(trimmed)
                val day = dayMatch?.value?.toInt() ?: return emptyList()
                
                val timeMatches = Regex("\\d{1,2}:\\d{2}").findAll(trimmed).toList()
                if (timeMatches.size < 2) return emptyList()
                
                try {
                    val date = LocalDate.of(
                        context.year,
                        context.monthValue,
                        day.coerceIn(1, 31)
                    )
                    val startTime = LocalTime.parse(timeMatches[0].value)
                    val endTime = LocalTime.parse(timeMatches[1].value)
                    
                    listOf(
                        Shift(
                            date = date,
                            startTime = startTime,
                            endTime = endTime,
                            type = ShiftType.DAY,
                            notes = input
                        )
                    )
                } catch (e: Exception) {
                    emptyList()
                }
            }

            // Day of week: Mon 8-4, Tue 12-8
            trimmed.matches(Regex("(?:mon|tue|wed|thu|fri|sat|sun)[a-z]*\\s+\\d{1,2}\\s*-\\s*\\d{1,2}")) -> {
                val dayNames = listOf(
                    "monday" to 1, "mon" to 1,
                    "tuesday" to 2, "tue" to 2,
                    "wednesday" to 3, "wed" to 3,
                    "thursday" to 4, "thu" to 4,
                    "friday" to 5, "fri" to 5,
                    "saturday" to 6, "sat" to 6,
                    "sunday" to 0, "sun" to 0
                )
                
                val dayOfWeek = dayNames.firstOrNull { (name, _) ->
                    trimmed.startsWith(name)
                }?.second ?: return emptyList()
                
                val timePart = trimmed.substringAfter(Regex("[a-z]{3}")).trim()
                val times = timePart.split("-")
                val startHour = times[0].trim().toIntOrNull() ?: return emptyList()
                val endHour = times[1].trim().toIntOrNull() ?: return emptyList()
                
                val startTime = LocalTime.of(startHour, 0)
                val endTime = LocalTime.of(endHour, 0)
                
                // Find next occurrence of this day
                var targetDate = context
                val targetDayOfWeek = (dayOfWeek + 1) % 7
                while (targetDate.dayOfWeek.value % 7 != targetDayOfWeek) {
                    targetDate = targetDate.plusDays(1)
                }
                
                listOf(
                    Shift(
                        date = targetDate,
                        startTime = startTime,
                        endTime = endTime,
                        type = ShiftType.DAY,
                        notes = input
                    )
                )
            }

            else -> emptyList()
        }
    }
}
