package com.example.weekendapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InterestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_interests)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nivelEnergia = intent.getStringExtra("NIVEL_ENERGIA")
        val companhia = intent.getStringExtra("COMPANHIA")
        val orcamento = intent.getStringExtra("ORCAMENTO")

        val checkGastronomia = findViewById<CheckBox>(R.id.checkGastronomia)
        val checkCultura = findViewById<CheckBox>(R.id.checkCultura)
        val checkArLivre = findViewById<CheckBox>(R.id.checkArLivre)
        val switchHorario = findViewById<Switch>(R.id.switchHorario)
        val btnVerResultado = findViewById<Button>(R.id.btnVerResultado)

        btnVerResultado.setOnClickListener {
            val interesses = ArrayList<String>()
            if (checkGastronomia.isChecked) interesses.add("gastro")
            if (checkCultura.isChecked) interesses.add("cultura")
            if (checkArLivre.isChecked) interesses.add("arlivre")

            val isDiurno = switchHorario.isChecked

            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("NIVEL_ENERGIA", nivelEnergia)
            intent.putExtra("COMPANHIA", companhia)
            intent.putExtra("ORCAMENTO", orcamento)
            intent.putStringArrayListExtra("INTERESSES", interesses)
            intent.putExtra("IS_DIURNO", isDiurno)

            startActivity(intent)
        }
    }
}