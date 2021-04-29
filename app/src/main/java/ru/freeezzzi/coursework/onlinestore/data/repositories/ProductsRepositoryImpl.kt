package ru.freeezzzi.coursework.onlinestore.data.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val serverAPI: ServerAPI,
    private val authRepositoryImpl: AuthRepositoryImpl
) : ProductsRepository {

    override suspend fun getProductsByCategory(categoryId: Long): OperationResult<List<Product>, String?> =
        try {
            val products = serverAPI.getProductsByCategory(authRepositoryImpl.loadUser()!!.token,categoryId).map {
                it.toProduct()
            }

            OperationResult.Success(products)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }

    override suspend fun getPopularProducts(): OperationResult<List<Product>, String?> =
        try {
            val products = serverAPI.getPopularProducts(authRepositoryImpl.loadUser()!!.token).map {
                it.toProduct()
            }

            OperationResult.Success(products)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }

    override suspend fun getProductsOnSale(): OperationResult<List<Product>, String?> =
        try {
            val products = serverAPI.getProductsOnSale(authRepositoryImpl.loadUser()!!.token).map {
                it.toProduct()
            }

            OperationResult.Success(products)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }
}
