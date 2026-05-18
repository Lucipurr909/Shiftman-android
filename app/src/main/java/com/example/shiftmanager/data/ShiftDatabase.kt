package com.example.shiftmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShiftEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShiftDatabase : RoomDatabase() {
    abstract fun shiftDao(): ShiftDao

    companion object {
        @Volatile
        private var instance: ShiftDatabase? = null

        fun getDatabase(context: Context): ShiftDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ShiftDatabase::class.java,
                    "shift_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
