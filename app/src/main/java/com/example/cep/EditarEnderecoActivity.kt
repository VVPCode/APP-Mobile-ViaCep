package com.example.cep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cep.data.AppDatabase
import com.example.cep.data.Endereco
import kotlinx.coroutines.launch

class EditarEnderecoActivity : AppCompatActivity() {

    private var endereco: Endereco? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_endereco)

        val etLogradouro = findViewById<EditText>(R.id.etLogradouro)
        val etBairro = findViewById<EditText>(R.id.etBairro)
        val etCidade = findViewById<EditText>(R.id.etCidade)
        val etEstado = findViewById<EditText>(R.id.etEstado)
        val etCep = findViewById<EditText>(R.id.etCep)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        // Recupera o endereço da intent
        endereco = intent.getSerializableExtra("endereco") as? Endereco

        if (endereco == null) {
            Toast.makeText(this, "Erro ao carregar endereço", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preenche os campos com os dados do endereço
        endereco?.let {
            etLogradouro.setText(it.logradouro)
            etBairro.setText(it.bairro)
            etCidade.setText(it.localidade)
            etEstado.setText(it.uf)
            etCep.setText(it.cep)
        }

        // Salvar alterações
        btnSalvar.setOnClickListener {
            val logradouro = etLogradouro.text.toString()
            val bairro = etBairro.text.toString()
            val cidade = etCidade.text.toString()
            val estado = etEstado.text.toString()
            val cep = etCep.text.toString()

            // Verificação simples de campos vazios
            if (logradouro.isBlank() || bairro.isBlank() || cidade.isBlank() || estado.isBlank() || cep.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val enderecoAtualizado = endereco!!.copy(
                logradouro = logradouro,
                bairro = bairro,
                localidade = cidade,
                uf = estado,
                cep = cep
            )

            lifecycleScope.launch {
                try {
                    val dao = AppDatabase.getInstance(applicationContext).enderecoDao()
                    dao.atualizar(enderecoAtualizado)
                    Toast.makeText(this@EditarEnderecoActivity, "Endereço atualizado!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@EditarEnderecoActivity, "Erro ao atualizar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
