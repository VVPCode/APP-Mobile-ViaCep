package com.example.cep.api

import com.example.cep.model.ViaCepResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("{cep}/json")
    suspend fun buscarCep(@Path("cep") cep: String): Response<ViaCepResponse>
}
