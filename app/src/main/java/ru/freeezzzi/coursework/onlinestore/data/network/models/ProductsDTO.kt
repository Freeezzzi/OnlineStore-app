package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsDTO(
    val id: Long,
    val title: String,
    val category: CategoryDTO,
    val price: String,
    val amount: Long,
    val imageUrl: String,
    val bought: Long,
    val country: String,
    val brand: String,
    val onSale: Boolean,
)
