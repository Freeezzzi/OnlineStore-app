package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe

@JsonClass(generateAdapter = true)
data class RecipeDTO(
    val id: Long? = null,
    val name: String = "",
    val difficulty: Int = Recipe.DIFFICULTY_EASY,
    val cookingTime: Int = 1, // 1 minute
    val imageUrl: String = "",
    val ingredientNames: List<String> = ArrayList(),
    val ingredientCount: List<String> = ArrayList(),
    val ingredientIds: List<Long> = ArrayList(),
    val stepsInfo: List<String> = ArrayList(),
    val stepsImages: List<String> = ArrayList(),
)

fun toRecipe(recipe: RecipeDTO): Recipe =
    Recipe(
        id = recipe.id ?: 0,
        name = recipe.name,
        difficulty = recipe.difficulty,
        cookingTime = recipe.cookingTime,
        imageUrl = recipe.imageUrl,
        ingredientNames = recipe.ingredientNames as ArrayList<String>,
        ingredientCount = recipe.ingredientCount as ArrayList<String>,
        ingredientProducts = ArrayList<Product>(),
        stepsInfo = recipe.stepsInfo as ArrayList<String>,
        stepsImages = recipe.stepsImages as ArrayList<String>
    )
