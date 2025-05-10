package com.example.cep.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cep.R
import com.example.cep.data.Endereco

class EnderecoAdapter(
    private val enderecos: MutableList<Endereco>,
    private val onEdit: (Endereco) -> Unit,
    private val onDelete: (Endereco) -> Unit
) : RecyclerView.Adapter<EnderecoAdapter.ViewHolder>() {

    // ViewHolder representa um item da lista
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEndereco: TextView = view.findViewById(R.id.tvEndereco)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_endereco, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = enderecos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val endereco = enderecos[position]

        holder.tvEndereco.text =
            "${endereco.logradouro}, ${endereco.bairro}, ${endereco.localidade} - ${endereco.uf} (${endereco.cep})"

        // Botão de editar
        holder.btnEditar.setOnClickListener { onEdit(endereco) }

        // Botão de excluir
        holder.btnExcluir.setOnClickListener { onDelete(endereco) }
    }

    // Atualiza os dados da lista
    fun updateData(novosEnderecos: List<Endereco>) {
        enderecos.clear()
        enderecos.addAll(novosEnderecos)
        notifyDataSetChanged()
    }
}
