package com.msedith.bitfit

class DataRepository(private val exerciseSessionDao: ExerciseSessionDao, private val exerciseDao: ExerciseDao) {
    fun getAllSessions(): List<ExerciseSessionData> = exerciseSessionDao.getAllSessions()
    fun insertSession(session: ExerciseSessionData) = exerciseSessionDao.insertSession(session)
}