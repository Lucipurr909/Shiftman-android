package com.example.shiftmanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shiftmanager.data.ShiftRepository
import com.example.shiftmanager.ui.screens.AddShiftScreen
import com.example.shiftmanager.ui.screens.CalendarScreen

@Composable
fun ShiftManagerApp(repository: ShiftRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "calendar"
    ) {
        composable("calendar") {
            CalendarScreen(
                repository = repository,
                onAddShiftClick = {
                    navController.navigate("add_shift")
                }
            )
        }

        composable("add_shift") {
            AddShiftScreen(
                repository = repository,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
