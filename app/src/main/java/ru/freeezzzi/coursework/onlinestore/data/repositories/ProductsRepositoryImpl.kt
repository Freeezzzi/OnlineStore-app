package ru.freeezzzi.coursework.onlinestore.data.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val serverAPI: ServerAPI
) : ProductsRepository {
    override suspend fun getAllProducts(): OperationResult<List<Product>, String?> {
        TODO("Not yet implemented")
    }
}
