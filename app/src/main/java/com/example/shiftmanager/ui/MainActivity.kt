package com.example.shiftmanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.shiftmanager.data.ShiftDatabase
import com.example.shiftmanager.data.ShiftRepository
import com.example.shiftmanager.ui.theme.ShiftmanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = ShiftDatabase.getDatabase(this)
        val repository = ShiftRepository(database.shiftDao())

        setContent {
            ShiftmanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ShiftManagerApp(repository = repository)
                }
            }
        }
    }
}
