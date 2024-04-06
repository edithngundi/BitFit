package com.msedith.bitfit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class SessionsFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var sessionsAdapter: SessionsAdapter
    private var sessionsList: MutableList<ExerciseSession> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sessions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        recyclerView = view.findViewById(R.id.sessionsRecyclerView) // Use the class-level variable
        recyclerView.layoutManager = LinearLayoutManager(context)
        sessionsAdapter = SessionsAdapter(requireContext(), sessionsList, object : SessionsAdapter.OnAddExerciseListener {
            override fun onAddExercise(session: ExerciseSession) {
                // Implement your logic to add an exercise to the session
                showAddExerciseDialog(session)
            }
        })
        recyclerView.adapter = sessionsAdapter

        viewModel.getSessionsList().observe(viewLifecycleOwner, { sessions ->
            sessionsAdapter.updateSessionExercises(sessions)
        })

        val fabAddSession: FloatingActionButton = view.findViewById(R.id.addSessionFab)
        fabAddSession.setOnClickListener {
            Log.d("SessionsFragment", "Showing add session dialog.")
            showAddSessionDialog()
        }
    }

    private fun showAddSessionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_session, null)
        val sessionNameEditText: EditText = dialogView.findViewById(R.id.sessionNameEditText)

        // Use requireContext() to get the context for the AlertDialog.Builder
        AlertDialog.Builder(requireContext())
            .setTitle("Add New Session")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, which ->
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

        AlertDialog.Builder(requireContext())
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