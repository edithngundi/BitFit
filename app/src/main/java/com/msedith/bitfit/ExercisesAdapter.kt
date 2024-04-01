package com.msedith.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExercisesAdapter(private var exercises: MutableList<Exercise> = mutableListOf()) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.exerciseNameTextView)
        private val setsTextView: TextView = view.findViewById(R.id.setsTextView)
        private val repsTextView: TextView = view.findViewById(R.id.repsTextView)
        private val weightTextView: TextView = view.findViewById(R.id.weightTextView)
        private val notesTextView: TextView = view.findViewById(R.id.notesTextView)

        fun bind(exercise: Exercise) {
            nameTextView.text = exercise.name
            setsTextView.text = "Sets: ${exercise.sets}"
            repsTextView.text = "Reps: ${exercise.reps}"
            weightTextView.text = "Weight: ${exercise.weight}"
            notesTextView.text = "Notes: ${exercise.note}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    fun updateExercises(newExercises: MutableList<Exercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}