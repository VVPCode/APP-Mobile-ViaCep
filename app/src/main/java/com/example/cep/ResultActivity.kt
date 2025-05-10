package com.example.cep

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cep.api.RetrofitClient
import com.example.cep.data.AppDatabase
import com.example.cep.data.Endereco
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvCep = findViewById<TextView>(R.id.tvCep)
        val tvLogradouro = findViewById<TextView>(R.id.tvLogradouro)
        val tvBairro = findViewById<TextView>(R.id.tvBairro)
        val tvCidade = findViewById<TextView>(R.id.tvCidade)
        val tvEstado = findViewById<TextView>(R.id.tvEstado)
        val btnVoltar = findViewById<Button>(R.id.buttonVoltar)

        val cep = intent.getStringExtra("CEP") ?: ""

        if (cep.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.service.buscarCep(cep)
                    if (response.isSuccessful) {
                        val dados = response.body()
                        if (dados != null) {
                            tvCep.text = "CEP: ${dados.cep}"
                            tvLogradouro.text = "Rua: ${dados.logradouro}"
                            tvBairro.text = "Bairro: ${dados.bairro}"
                            tvCidade.text = "Cidade: ${dados.localidade}"
                            tvEstado.text = "Estado: ${dados.uf}"

                            // Salvar no banco de dados
                            val endereco = Endereco(
                                cep = dados.cep ?: "",
                                logradouro = dados.logradouro ?: "",
                                bairro = dados.bairro ?: "",
                                localidade = dados.localidade ?: "",
                                uf = dados.uf ?: ""
                            )

                            withContext(Dispatchers.IO) {
                                AppDatabase.getInstance(this@ResultActivity)
                                    .enderecoDao()
                                    .inserir(endereco)
                            }

                            Toast.makeText(this@ResultActivity, "Endereço salvo!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        tvCep.text = "CEP não encontrado."
                    }
                } catch (e: Exception) {
                    tvCep.text = "Erro: ${e.message}"
                }
            }
        } else {
            tvCep.text = "CEP inválido."
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
