package ru.freeezzzi.coursework.onlinestore.data.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.data.network.models.RecipeDTO
import ru.freeezzzi.coursework.onlinestore.data.network.models.toRecipe
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe
import ru.freeezzzi.coursework.onlinestore.domain.repositories.RecipesRepository
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val serverAPI: ServerAPI,
    private val authRepositoryImpl: AuthRepositoryImpl,
) : RecipesRepository {
    override suspend fun getAll(): OperationResult<List<Recipe>, String?> =
        try {
            val recipes = serverAPI.getRecipes(authRepositoryImpl.loadUser()!!.token).map {
                val recipe = toRecipe(it)
                recipe.ingredientProducts = serverAPI.getProductsByIds(authRepositoryImpl.loadUser()!!.token, it.ingredientIds).map {
                    it.toProduct()
                } as ArrayList<Product>
                recipe
            }

            OperationResult.Success(recipes)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }

    override suspend fun addNewRecipe(recipeDTO: RecipeDTO): OperationResult<Boolean, String?> =
        try {
            val result = serverAPI.addRecipe(authRepositoryImpl.loadUser()!!.token, recipeDTO)

            OperationResult.Success(result)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }


}
