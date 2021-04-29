package ru.freeezzzi.coursework.onlinestore.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.freeezzzi.coursework.onlinestore.data.network.models.LoginRequestDTO
import ru.freeezzzi.coursework.onlinestore.data.network.models.LoginResponseDTO
import ru.freeezzzi.coursework.onlinestore.data.network.models.RegisterRequestDTO

interface ServerAPI {

    @POST("login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ): LoginResponseDTO

    @POST("register")
    suspend fun register(
        @Body registerRequestDTO: RegisterRequestDTO
    ): LoginResponseDTO
}
