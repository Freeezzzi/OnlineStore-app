package ru.freeezzzi.coursework.onlinestore.domain.models

data class Order(
    val id: Long,
    val user_id: Long,
    val status: Int,
    val products: ArrayList<Product>, // В продукте в поле itemCountInCart хранится кол-во предметов в заказе
    val address: String,
    val orderTime: String,
    val deliveryTime: String
) {
    companion object {
        const val STATUS_PLACED = 0
        const val STATUS_PREPARING = 1
        const val STATUS_ONTHEWAY = 2
        const val STATUS_DELIVERED = 3
        const val ORDERS_ALL = 4 // только для передачи в качестве параметра для фильтра
    }
}
