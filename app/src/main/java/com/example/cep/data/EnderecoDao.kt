package com.example.cep.data

import androidx.room.*

@Dao
interface EnderecoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(endereco: Endereco)

    @Query("SELECT * FROM enderecos")
    suspend fun listarTodos(): List<Endereco>

    @Delete
    suspend fun deletar(endereco: Endereco)

    @Update
    suspend fun atualizar(endereco: Endereco)
}
