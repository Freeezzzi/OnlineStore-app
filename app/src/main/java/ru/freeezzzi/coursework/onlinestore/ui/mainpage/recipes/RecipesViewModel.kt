package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
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
}
