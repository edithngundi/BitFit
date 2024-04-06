package com.msedith.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class DashboardFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the sessionsList LiveData from the ViewModel
        viewModel.getSessionsList().observe(viewLifecycleOwner, Observer { sessions ->
            // Call a function to calculate and display the average when sessions data changes
            calculateAndDisplayAverage(sessions, view)
        })
    }

    private fun calculateAndDisplayAverage(sessions: MutableList<ExerciseSession>, view: View) {
        val averageTextView: TextView = view.findViewById(R.id.averageExercisesTextView)

        // Calculate the average number of exercises per session
        val totalExercises = sessions.sumOf { it.exercises.size }
        val averageExercises = if (sessions.isNotEmpty()) totalExercises / sessions.size else 0

        // Set the calculated average to the TextView
        averageTextView.text = getString(R.string.average_exercises_per_session, averageExercises)
    }
}