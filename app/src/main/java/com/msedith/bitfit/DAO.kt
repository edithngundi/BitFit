package com.msedith.bitfit

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import java.util.UUID

@Dao
interface ExerciseSessionDao {
    @Query("SELECT * FROM exercise_session")
    fun getAllSessions(): List<ExerciseSessionData>

    @Insert
    fun insertSession(session: ExerciseSessionData)

    @Update
    fun updateSession(session: ExerciseSessionData)

    @Delete
    fun deleteSession(session: ExerciseSessionData)
}

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise WHERE sessionId = :sessionId")
    fun getExercisesForSession(sessionId: UUID): List<ExerciseData>

    @Insert
    fun insertExercise(exercise: ExerciseData)

    @Update
    fun updateExercise(exercise: ExerciseData)

    @Delete
    fun deleteExercise(exercise: ExerciseData)
}
