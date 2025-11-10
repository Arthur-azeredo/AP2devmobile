package com.example.weekendapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EnergyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_energy)

        val radioGroupEnergia = findViewById<RadioGroup>(R.id.radioGroupEnergia)
        val btnProximo = findViewById<Button>(R.id.btnProximoEnergia)

        btnProximo.setOnClickListener {
            val selectedId = radioGroupEnergia.checkedRadioButtonId

            val selectedValue = when (selectedId) {
                R.id.radioEremita -> "eremita"
                R.id.radioRelax -> "relax"
                R.id.radioAgito -> "agito"
                else -> ""
            }

            val intent = Intent(this, CompanyBudgetActivity::class.java)
            intent.putExtra("NIVEL_ENERGIA", selectedValue)
            startActivity(intent)
        }
    }
}