package ru.freeezzzi.coursework.onlinestore.domain.models

data class Order(
    val id: Long,
    val user_id: Long,
    val status: String,
    val products: ArrayList<Product>, // В продукте в поле itemCountInCart хранится кол-во предметов в заказе
    val address: String,
    val orderTime: String,
    val deliveryTime: String
)
