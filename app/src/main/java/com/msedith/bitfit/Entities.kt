package com.msedith.bitfit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.UUID

@Entity(tableName = "exercise")
data class ExerciseData(
    @PrimaryKey val exerciseId: UUID = UUID.randomUUID(),
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val note: String
)

@Entity(tableName = "exercise_session")
data class ExerciseSessionData(
    @PrimaryKey val sessionId: UUID = UUID.randomUUID(),
    val name: String,
)
