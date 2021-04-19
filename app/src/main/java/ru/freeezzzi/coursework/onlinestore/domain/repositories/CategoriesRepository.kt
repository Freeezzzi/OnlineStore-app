package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Category

interface CategoriesRepository {

    suspend fun getCategories() : OperationResult<List<Category>, String?>
}