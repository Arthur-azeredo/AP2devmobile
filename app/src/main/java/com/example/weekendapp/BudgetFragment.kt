package com.example.weekendapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup

class BudgetFragment : Fragment() {
    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_budget, container, false)
        radioGroup = view.findViewById(R.id.radioGroupBudget)
        return view
    }

    fun getSelectedBudget(): String {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.radioGratis -> "gratis"
            R.id.radioBarato -> "barato"
            R.id.radioSemLimite -> "semlimite"
            else -> "gratis"
        }
    }
}