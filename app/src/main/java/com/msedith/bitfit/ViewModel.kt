package com.msedith.bitfit

import androidx.lifecycle.ViewModel

class SessionViewModel(private val repository: DataRepository) : ViewModel() {
    val allSessions = repository.getAllSessions()

    fun insertSession(session: ExerciseSessionData) {
        repository.insertSession(session)
    }
}