package com.msedith.bitfit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class SharedViewModel : ViewModel() {
    private val sessionsList = MutableLiveData<MutableList<ExerciseSession>>(mutableListOf())

    fun getSessionsList(): LiveData<MutableList<ExerciseSession>> = sessionsList

    fun addSession(session: ExerciseSession) {
        // If currentList is null, initialize it with mutableListOf()
        val currentList = sessionsList.value ?: mutableListOf()
        currentList.add(session)
        sessionsList.value = currentList // Now currentList is guaranteed to be non-null
    }

    fun addExerciseToSession(sessionId: UUID, exercise: Exercise) {
        // If currentList is null, initialize it with mutableListOf()
        val currentList = sessionsList.value ?: mutableListOf()
        // Find the session by ID and add the exercise to it
        val session = currentList.find { it.id == sessionId }
        session?.exercises?.add(exercise)
        // Update the value of sessionsList to notify observers
        sessionsList.value = currentList
    }

    // Example method to calculate the average number of exercises per session
    fun calculateAverageExercisesPerSession(): Int {
        val currentList = sessionsList.value ?: return 0 // Return 0 if the list is null
        if (currentList.isEmpty()) return 0
        val totalExercises = currentList.sumOf { it.exercises.size }
        return totalExercises / currentList.size
    }
}