package ru.freeezzzi.coursework.onlinestore.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.freeezzzi.coursework.onlinestore.data.network.models.*

interface ServerAPI {

    @POST("login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ): LoginResponseDTO

    @POST("register")
    suspend fun register(
        @Body registerRequestDTO: RegisterRequestDTO
    ): LoginResponseDTO

    @GET("categories/all")
    suspend fun getAllCategories(
        @Query("Token")token: String
    ): List<CategoryDTO>

    @GET("products/popular")
    suspend fun getPopularProducts(
        @Query("Token")token: String
    ): List<ProductsDTO>

    @GET("products/by-category")
    suspend fun getProductsByCategory(
        @Query("Token")token: String,
        @Query(value = "categoryId") categoryId: Long
    ): List<ProductsDTO>

    @GET("products/on-sale")
    suspend fun getProductsOnSale(
        @Query("Token")token: String
    ): List<ProductsDTO>
}
