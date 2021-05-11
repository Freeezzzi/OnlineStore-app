package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.data.network.models.RecipeDTO
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe
import ru.freeezzzi.coursework.onlinestore.domain.repositories.RecipesRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {
    private val mutableRecipesList = MutableLiveData<ViewState<List<Recipe>, String?>>()
    val recipesList: LiveData<ViewState<List<Recipe>, String?>> get() = mutableRecipesList
    private var fullRecipesList: List<Recipe> = mutableListOf()

    fun getRecipes(difficulty: Int) { // Order companion object
        viewModelScope.launch {
            mutableRecipesList.value = ViewState.loading()
            when (val result = recipesRepository.getAll()) {
                is OperationResult.Success -> {
                    fullRecipesList = result.data
                    if (difficulty == Recipe.DIFFICULTY_ALL) {
                        mutableRecipesList.value = ViewState.success(fullRecipesList)
                        return@launch
                    }
                    val requiredList = mutableListOf<Recipe>()
                    fullRecipesList.forEach {
                        if (it.difficulty == difficulty) requiredList.add(it)
                    }
                    mutableRecipesList.value = ViewState.success(requiredList)
                }
                is OperationResult.Error -> {
                    val requiredList = mutableListOf<Recipe>()
                    fullRecipesList.forEach {
                        if (it.difficulty == difficulty) requiredList.add(it)
                    }
                    mutableRecipesList.value = ViewState.error(requiredList, result.data)
                }
            }
        }
    }

    fun filterRecipes(difficulty: Int) {
        if (difficulty == Recipe.DIFFICULTY_ALL) {
            mutableRecipesList.value = ViewState.success(fullRecipesList)
            return
        }
        val requiredList = mutableListOf<Recipe>()
        fullRecipesList.forEach {
            if (it.difficulty == difficulty) requiredList.add(it)
        }
        mutableRecipesList.value = ViewState.success(requiredList)
    }

    fun addRecipe() {
        viewModelScope.launch {
            val recipe = RecipeDTO(
                id = 0,
                name = "Hot and Sour Soup",
                difficulty = Recipe.DIFFICULTY_EASY,
                cookingTime = 75,
                imageUrl = "https://dinnerthendessert.com/wp-content/uploads/2019/02/Hot-and-Sour-Soup.jpg",
                ingredientNames = arrayListOf("Carrot", "Cabbage", "Black mushrooms", "Bamboo shoots", "Chicken", "Soy sauce", "Chicken Stock", "Chilli oil"),
                ingredientCount = arrayListOf("x2", "20g", "x2", "10g", "100g", "1 tbsp", "2 cups", "1 tbsp"),
                ingredientIds = arrayListOf(6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L),
                stepsInfo = arrayListOf(
                    "Add the mushrooms to boiling water and let sit in a small bowl for 30 minutes before slicing very thinly and discarding any pieces that are still very tough.",
                    "Add the green onions, garlic cloves, onion and peppercorns to a bouquet garni bag or to a large dutch oven or heavy bottomed pot with the chicken stock and bring to a simmer for 20 minutes until reduced by 1/3 (If you didn't use a bag, fish out the ingredients) then keep on low heat while you continue cooking.",
                    "In a large skillet add the oil on medium-high heat and cook the pork tenderloin then remove from the pan and add in the ginger, bamboo shoots, chile paste, soy sauce, rice vinegar, white pepper and sugar stirring well to combine and bring to a boil before moving the mixture to the dutch oven with the chicken stock.",
                    "Add the mushrooms to the pot along with the tofu  and the cornstarch/water mix and bring the heat back up to medium-low for 10 minutes.",
                    "Stir the soup in a clock-wise direction at a slow speed and slowly pour in the eggs while whisking slowly to cook the egg while it also spreads out while it cooks (be careful not to let it cook in a giant puddle or in too thin of a stream).",
                    "Garnish with green onions if desired."
                ),
                stepsImages = arrayListOf(
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup-recipe11.jpg",
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup-recipe14.jpg",
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup-recipe8.jpg",
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup-recipe15.jpg",
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup-recipe13.jpg",
                    "https://www.vegrecipesofindia.com/wp-content/uploads/2014/11/vegetable-hot-and-sour-soup.jpg"
                )
            )
            recipesRepository.addNewRecipe(recipe)
        }
    }
}
