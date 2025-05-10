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

    // ViewHolder define as referências para os elementos de cada item
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEndereco: TextView = view.findViewById(R.id.tvEndereco)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)
    }

    // Cria o layout do item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_endereco, parent, false)
        return ViewHolder(view)
    }

    // Retorna o número de itens na lista
    override fun getItemCount(): Int = enderecos.size

    // Associa os dados a cada item da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val endereco = enderecos[position]

        holder.tvEndereco.text = "${endereco.logradouro}, ${endereco.bairro}, ${endereco.localidade} - ${endereco.uf} (${endereco.cep})"

        holder.btnEditar.setOnClickListener { onEdit(endereco) }
        holder.btnExcluir.setOnClickListener { onDelete(endereco) }
    }

    // Atualiza a lista de endereços na interface
    fun updateData(novosEnderecos: List<Endereco>) {
        enderecos.clear()
        enderecos.addAll(novosEnderecos)
        notifyDataSetChanged()
    }
}
