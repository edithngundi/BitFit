package com.msedith.bitfit

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExerciseSession::class, Exercise::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseSessionDao(): ExerciseSessionDao
    abstract fun exerciseDao(): ExerciseDao
}