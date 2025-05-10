package com.example.cep

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtCep = findViewById<EditText>(R.id.editTextCep)
        val btnBuscar = findViewById<Button>(R.id.buttonSearch)

        btnBuscar.setOnClickListener {
            val cep = edtCep.text.toString()
            if (cep.length == 8) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("CEP", cep)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Digite um CEP válido com 8 números", Toast.LENGTH_SHORT).show()
            }
        }

        // NOVO BOTÃO PARA ABRIR A TELA DE ENDEREÇOS SALVOS
        val btnEnderecosSalvos = findViewById<Button>(R.id.buttonEnderecosSalvos)
        btnEnderecosSalvos.setOnClickListener {
            val intent = Intent(this, EnderecosSalvosActivity::class.java)
            startActivity(intent)
        }
    }
}
