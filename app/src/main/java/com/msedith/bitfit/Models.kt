package com.msedith.bitfit

import java.util.UUID

data class ExerciseSession(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val exercises: MutableList<Exercise> = mutableListOf()
)

data class Exercise(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val note: String
)