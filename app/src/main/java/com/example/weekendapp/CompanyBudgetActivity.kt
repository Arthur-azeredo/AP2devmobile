package com.example.weekendapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CompanyBudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_budget)

        val nivelEnergia = intent.getStringExtra("NIVEL_ENERGIA")

        val spinnerCompanhia = findViewById<Spinner>(R.id.spinnerCompanhia)
        val btnProximo = findViewById<Button>(R.id.btnProximoCompanhia)

        btnProximo.setOnClickListener {
            val companhia = spinnerCompanhia.selectedItem.toString()

            val budgetFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as BudgetFragment
            val orcamento = budgetFragment.getSelectedBudget()

            val intent = Intent(this, InterestsActivity::class.java)

            intent.putExtra("NIVEL_ENERGIA", nivelEnergia)
            intent.putExtra("COMPANHIA", companhia)
            intent.putExtra("ORCAMENTO", orcamento)

            startActivity(intent)
        }
    }
}