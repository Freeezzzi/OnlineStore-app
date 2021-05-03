package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Order

interface OrdersRepository {
    suspend fun placeOrder(order: Order): OperationResult<Boolean, String?>

    suspend fun getOrders(user_id: Long): OperationResult<List<Order>, String?>
}