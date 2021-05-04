package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersDTO(
    val id: Long,
    val user_id: Long,
    val status: Int,
    val productsIDs: List<Long>,
    val productsCount: List<Int>,
    val address: String,
    val orderTime: String,
    val deliveryTime: String
) 
