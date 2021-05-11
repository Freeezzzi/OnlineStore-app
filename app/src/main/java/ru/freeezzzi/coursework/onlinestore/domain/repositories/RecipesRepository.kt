package ru.freeezzzi.coursework.onlinestore.domain.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.models.RecipeDTO
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe

interface RecipesRepository {
    suspend fun getAll(): OperationResult<List<Recipe>, String?>

    suspend fun addNewRecipe(recipeDTO: RecipeDTO): OperationResult<Boolean, String?>
}
