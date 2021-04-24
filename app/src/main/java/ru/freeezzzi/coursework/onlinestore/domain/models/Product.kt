package ru.freeezzzi.coursework.onlinestore.domain.models

data class Product(
    val id: Long?,
    val title: String,
    val category: Category,
    val price: String,
    val amount: Long,
    val imageUrl: String,
    val bought: Long,
    val country: String,
    val brand: String,
    val onSale: Boolean
)
