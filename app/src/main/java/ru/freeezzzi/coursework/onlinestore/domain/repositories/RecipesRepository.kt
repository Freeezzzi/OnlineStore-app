package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe

interface RecipesRepository {
    suspend fun getAll(): OperationResult<List<Recipe>, String?>
}
