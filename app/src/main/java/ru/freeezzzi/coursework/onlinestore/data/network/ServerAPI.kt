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

    @POST("orders/place")
    suspend fun placeOrder(
        @Query("Token")token: String,
        @Body ordersDTO: OrdersDTO
    ): Boolean

    @GET("orders/by_id")
    suspend fun findOrdersForUser(
        @Query("Token")token: String,
        @Query(value = "user_id") userId: Long
    ): List<OrdersDTO>

    @GET("products/by_ids")
    suspend fun getProductsByIds(
        @Query("Token")token: String,
        @Query(value = "productIds") productIds: List<Long>
    ): List<ProductsDTO>

    @POST("user/update")
    suspend fun updateUser(
        @Query("Token")token: String,
        @Body userProfileDTO: UserProfileDTO
    ): Boolean

    @GET("recipes/all")
    suspend fun getRecipes(
        @Query("Token")token: String
    ): List<RecipeDTO>
}
