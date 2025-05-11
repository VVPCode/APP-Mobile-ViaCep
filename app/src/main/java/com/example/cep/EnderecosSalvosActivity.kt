package com.example.cep

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cep.adapter.EnderecoAdapter
import com.example.cep.data.AppDatabase
import com.example.cep.data.Endereco
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnderecosSalvosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EnderecoAdapter
    private val listaEnderecos = mutableListOf<Endereco>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enderecos_salvos)

        recyclerView = findViewById(R.id.recyclerViewEnderecos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EnderecoAdapter(listaEnderecos,
            onEdit = { endereco -> editarEndereco(endereco) },
            onDelete = { endereco -> excluirEndereco(endereco) }
        )

        recyclerView.adapter = adapter

        // Botão Voltar
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        carregarEnderecos()
    }

    override fun onResume() {
        super.onResume()
        carregarEnderecos()
    }

    private fun carregarEnderecos() {
        lifecycleScope.launch {
            val dao = AppDatabase.getInstance(applicationContext).enderecoDao()
            val enderecos = withContext(Dispatchers.IO) {
                dao.listarTodos()
            }
            adapter.updateData(enderecos)
        }
    }

    private fun excluirEndereco(endereco: Endereco) {
        lifecycleScope.launch {
            val dao = AppDatabase.getInstance(applicationContext).enderecoDao()
            withContext(Dispatchers.IO) {
                dao.deletar(endereco)
            }
            Toast.makeText(this@EnderecosSalvosActivity, "Endereço excluído", Toast.LENGTH_SHORT).show()
            carregarEnderecos()
        }
    }

    private fun editarEndereco(endereco: Endereco) {
        val intent = Intent(this, EditarEnderecoActivity::class.java)
        intent.putExtra("endereco", endereco)
        startActivity(intent)
    }
}
