package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

interface ProductsRepository {
    suspend fun getAllProducts(): OperationResult<List<Product>, String?>
}