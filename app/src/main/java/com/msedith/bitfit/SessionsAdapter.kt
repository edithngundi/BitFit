package com.msedith.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SessionsAdapter(
    private val context: Context,
    private val sessions: MutableList<ExerciseSession>,
    private val onAddExerciseListener: OnAddExerciseListener
) : RecyclerView.Adapter<SessionsAdapter.SessionViewHolder>() {

    interface OnAddExerciseListener {
        fun onAddExercise(session: ExerciseSession)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        return SessionViewHolder(view, context, onAddExerciseListener)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessions[position])
    }

    override fun getItemCount(): Int = sessions.size

    inner class SessionViewHolder(
        view: View,
        private val context: Context,
        private val onAddExerciseListener: OnAddExerciseListener
    ) : RecyclerView.ViewHolder(view) {
        private val sessionNameTextView: TextView = view.findViewById(R.id.sessionNameTextView)
        private val fabAddExercise: FloatingActionButton = view.findViewById(R.id.fabAddExercise)
        private val exercisesRecyclerView: RecyclerView = view.findViewById(R.id.exercisesRecyclerView)
        private val exercisesAdapter: ExercisesAdapter = ExercisesAdapter()

        init {
            exercisesRecyclerView.layoutManager = LinearLayoutManager(context)
            exercisesRecyclerView.adapter = exercisesAdapter
        }

        fun bind(session: ExerciseSession) {
            sessionNameTextView.text = session.name
            exercisesAdapter.updateExercises(session.exercises)

            fabAddExercise.setOnClickListener {
                onAddExerciseListener.onAddExercise(session)
            }
        }
    }

    fun updateSessionExercises(sessionIndex: Int) {
        notifyItemChanged(sessionIndex)
    }
}
