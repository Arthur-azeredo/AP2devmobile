package com.example.weekendapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Recomendacao(
    val titulo: String,
    val descricao: String,
    val mapQuery: String,
    val imageResId: Int
)

class ResultActivity : AppCompatActivity() {

    private lateinit var recomendacaoFinal: Recomendacao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nivelEnergia = intent.getStringExtra("NIVEL_ENERGIA")
        val companhia = intent.getStringExtra("COMPANHIA")
        val orcamento = intent.getStringExtra("ORCAMENTO")
        val interesses = intent.getStringArrayListExtra("INTERESSES")
        val isDiurno = intent.getBooleanExtra("IS_DIURNO", true)

        recomendacaoFinal = gerarRecomendacao(nivelEnergia, companhia, orcamento, interesses, isDiurno)

        val backgroundImage = findViewById<ImageView>(R.id.background_image)
        val txtTitulo = findViewById<TextView>(R.id.txtResultadoTitulo)
        val txtDescricao = findViewById<TextView>(R.id.txtResultadoDescricao)

        txtTitulo.text = recomendacaoFinal.titulo
        txtDescricao.text = recomendacaoFinal.descricao

        if (recomendacaoFinal.imageResId != 0) {
            backgroundImage.setImageResource(recomendacaoFinal.imageResId)
        }

        val btnMapa = findViewById<Button>(R.id.btnAbrirMapa)
        val btnShare = findViewById<Button>(R.id.btnCompartilhar)
        val btnNovaAtividade = findViewById<Button>(R.id.btnNovaAtividade)

        btnMapa.setOnClickListener {
            val query = Uri.encode(recomendacaoFinal.mapQuery)
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                val webMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/$query"))
                startActivity(webMapIntent)
            }
        }

        btnShare.setOnClickListener {
            val textoCompartilhar = "Meu plano para o FDS: ${recomendacaoFinal.titulo}! ${recomendacaoFinal.descricao}"

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textoCompartilhar)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Compartilhar este plano via...")
            startActivity(shareIntent)
        }

        btnNovaAtividade.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun gerarRecomendacao(
        energia: String?,
        companhia: String?,
        orcamento: String?,
        interesses: ArrayList<String>?,
        isDiurno: Boolean
    ): Recomendacao {

        if (interesses != null) {
            if (interesses.contains("gastro")) {
                return Recomendacao(
                    titulo = "Experiência Gastronômica",
                    descricao = "Que tal conhecer um restaurante novo ou cozinhar algo especial?",
                    mapQuery = if (orcamento == "gratis") "receitas fáceis" else "restaurantes próximos",
                    imageResId = R.drawable.bg_result_gastro
                )
            }
            if (interesses.contains("cultura")) {
                return Recomendacao(
                    titulo = "Passeio Cultural",
                    descricao = "Visite um museu, uma exposição de arte ou assista a uma peça de teatro.",
                    mapQuery = "museus próximos",
                    imageResId = R.drawable.bg_result_cultura
                )
            }
            if (interesses.contains("arlivre") && orcamento == "gratis") {
                return Recomendacao(
                    titulo = "Passeio no Parque",
                    descricao = "Aproveite o dia (ou a noite) para caminhar, andar de bicicleta ou fazer um piquenique.",
                    mapQuery = "parques próximos",
                    imageResId = R.drawable.bg_result_arlivre
                )
            }
        }

        if (energia == "eremita") {
            return Recomendacao(
                titulo = "Maratona de Séries",
                descricao = "Nada melhor que seu sofá! Prepare a pipoca, escolha um streaming e relaxe.",
                mapQuery = "supermercado pipoca",
                imageResId = R.drawable.bg_result_series
            )
        }

        if (energia == "agito" && companhia == "com amigos" && orcamento != "gratis") {
            return Recomendacao(
                titulo = "Bar ou Balada",
                descricao = "Junte os amigos e aproveite a noite! Uma boa música e conversas são a pedida.",
                mapQuery = "bares ${if (isDiurno) "com happy hour" else "próximos"}",
                imageResId = R.drawable.bg_result_balada
            )
        }

        return Recomendacao(
            titulo = "Ver um Filme",
            descricao = "Um clássico nunca falha. Veja o que está em cartaz no cinema ou no streaming.",
            mapQuery = "cinema",
            imageResId = R.drawable.bg_result_cinema
        )
    }
}