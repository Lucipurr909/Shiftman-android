package com.example.shiftmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftmanager.data.ShiftRepository
import com.example.shiftmanager.domain.Shift
import com.example.shiftmanager.domain.ShiftType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    repository: ShiftRepository,
    onAddShiftClick: () -> Unit
) {
    val shifts = repository.getAllShifts().collectAsState(initial = emptyList())
    val groupedShifts = shifts.value.groupBy { it.date }.toSortedMap()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddShiftClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Shift")
            }
        }
    ) { innerPadding ->
        if (groupedShifts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No shifts scheduled. Tap + to add one.", fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groupedShifts.forEach { (date, shiftList) ->
                    item {
                        Text(
                            text = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(shiftList) { shift ->
                        ShiftCard(shift)
                    }
                }
            }
        }
    }
}

@Composable
fun ShiftCard(shift: Shift) {
    val typeColor = when (shift.type) {
        ShiftType.DAY -> Color(0xFF4CAF50) // Green
        ShiftType.NIGHT -> Color(0xFF1A237E) // Dark Blue
        ShiftType.EXTRA -> Color(0xFFFF9800) // Orange
        ShiftType.OFF -> Color(0xFF9E9E9E) // Gray
        ShiftType.VACATION -> Color(0xFF2196F3) // Blue
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(typeColor, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            ) {
                Text(
                    text = shift.type.name.take(1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = shift.type.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (shift.startTime != null && shift.endTime != null) {
                    Text(
                        text = "${shift.startTime} - ${shift.endTime}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                if (shift.notes.isNotEmpty()) {
                    Text(
                        text = shift.notes,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
