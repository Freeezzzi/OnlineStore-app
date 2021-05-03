package ru.freeezzzi.coursework.onlinestore.data.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.data.network.models.OrdersDTO
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val serverAPI: ServerAPI,
    private val authRepositoryImpl: AuthRepositoryImpl,
) : OrdersRepository {
    override suspend fun placeOrder(order: Order): OperationResult<Boolean, String?> =
        try {
            val productsIds = ArrayList<Long>()
            val productsCount = ArrayList<Int>()
            order.products.forEach {
                productsIds.add(it.id!!)
                productsCount.add(it.countInCart)
            }
            val orderDTO = OrdersDTO(
                id = 0,
                user_id = authRepositoryImpl.loadUser()!!.id,
                status = order.status,
                productsIDs = productsIds,
                productsCount = productsCount,
                address = order.address,
                orderTime = order.orderTime,
                deliveryTime = order.deliveryTime
            )
            val result = serverAPI.placeOrder(authRepositoryImpl.loadUser()!!.token, orderDTO)

            OperationResult.Success(result)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }

    override suspend fun getOrders(user_id: Long): OperationResult<List<Order>, String?> =
        try {
            val user = authRepositoryImpl.loadUser()!!
            val ordersDTOs = serverAPI.findOrdersForUser(user.token, user.id)
            val orders = ordersDTOs.map {
                val products: ArrayList<Product> = serverAPI.getProductsByIds(user.token, it.productsIDs).map {
                    it.toProduct()
                } as ArrayList<Product>
                products.forEachIndexed { index, product ->
                    product.countInCart = it.productsCount.get(index)
                }
                Order(
                    id = it.id,
                    user_id = it.user_id,
                    status = it.status,
                    products = products,
                    address = it.address,
                    orderTime = it.orderTime,
                    deliveryTime = it.deliveryTime
                )
            }

            OperationResult.Success(orders)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }
}
