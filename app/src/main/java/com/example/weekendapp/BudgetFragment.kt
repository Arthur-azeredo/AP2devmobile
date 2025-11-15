package com.example.weekendapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnProximo = requireActivity().findViewById<Button>(R.id.btnProximoCompanhia)

        btnProximo.setOnClickListener {
            val spinnerCompanhia = requireActivity().findViewById<Spinner>(R.id.spinnerCompanhia)
            val companhia = spinnerCompanhia.selectedItem.toString()

            val nivelEnergia = requireActivity().intent.getStringExtra("NIVEL_ENERGIA")
            val orcamento = getSelectedBudget()

            val intent = Intent(requireActivity(), InterestsActivity::class.java).apply {
                putExtra("NIVEL_ENERGIA", nivelEnergia)
                putExtra("COMPANHIA", companhia)
                putExtra("ORCAMENTO", orcamento)
            }
            startActivity(intent)
        }
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