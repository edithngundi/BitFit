package com.msedith.bitfit

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class MainActivity : AppCompatActivity(), SessionsAdapter.OnAddExerciseListener {
    private lateinit var recyclerView: RecyclerView
    private var sessionsList: MutableList<ExerciseSession> = mutableListOf()
    private lateinit var sessionsAdapter: SessionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionsAdapter = SessionsAdapter(this, sessionsList, this)
        recyclerView = findViewById(R.id.sessionsRecyclerView)
        recyclerView.adapter = sessionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addSessionFab: FloatingActionButton = findViewById(R.id.addSessionFab)
        addSessionFab.setOnClickListener {
            showAddSessionDialog()
        }
    }

    override fun onAddExercise(session: ExerciseSession) {
        showAddExerciseDialog(session)
    }

    private fun showAddSessionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_session, null)
        val sessionNameEditText = dialogView.findViewById<EditText>(R.id.sessionNameEditText)

        AlertDialog.Builder(this)
            .setTitle("Add New Session")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val sessionName = sessionNameEditText.text.toString().trim()
                if (sessionName.isNotEmpty()) {
                    val newSession = ExerciseSession(name = sessionName)
                    sessionsList.add(newSession)
                    sessionsAdapter.notifyItemInserted(sessionsList.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddExerciseDialog(session: ExerciseSession) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_exercise, null)
        val exerciseNameEditText: EditText = dialogView.findViewById(R.id.exerciseNameEditText)
        val setsEditText: EditText = dialogView.findViewById(R.id.setsEditText)
        val repsEditText: EditText = dialogView.findViewById(R.id.repsEditText)
        val weightEditText: EditText = dialogView.findViewById(R.id.weightEditText)
        val notesEditText: EditText = dialogView.findViewById(R.id.notesEditText)

        AlertDialog.Builder(this)
            .setTitle("Add Exercise")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, which ->
                val name = exerciseNameEditText.text.toString()
                val sets = setsEditText.text.toString().toIntOrNull() ?: 0
                val reps = repsEditText.text.toString().toIntOrNull() ?: 0
                val weight = weightEditText.text.toString().toDoubleOrNull() ?: 0.0
                val notes = notesEditText.text.toString()

                val newExercise = Exercise(UUID.randomUUID(), name, sets, reps, weight, notes)
                session.exercises.add(newExercise)
                val sessionIndex = sessionsList.indexOf(session)
                if(sessionIndex != -1) {
                    sessionsAdapter.notifyItemChanged(sessionIndex)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}