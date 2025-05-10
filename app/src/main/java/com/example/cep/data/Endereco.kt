package com.example.cep.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// No arquivo Endereco.kt
@Entity(tableName = "enderecos")
data class Endereco(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String
) : Serializable

