package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

interface ProductsRepository {
    suspend fun getProductsByCategory(categoryId: Long): OperationResult<List<Product>, String?>

    suspend fun getPopularProducts(): OperationResult<List<Product>, String?>

    suspend fun getProductsOnSale(): OperationResult<List<Product>, String?>
}
