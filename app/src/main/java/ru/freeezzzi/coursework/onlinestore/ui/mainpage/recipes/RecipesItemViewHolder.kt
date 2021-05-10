package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeCardBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class RecipesItemViewHolder(
    private val binding: RecipeCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        recipe: Recipe,
        itemOnClickAction: (Recipe) -> Unit
    ) {
        binding.recipeCardImage.setPicture(recipe.imageUrl)
        binding.recipeCardLabel.text = recipe.name
        binding.recipeCardTime.text = recipe.cookingTime.toString() + "m"
        //TODO Show difficulty
        binding.recipeCardDifficulty.text = recipe.difficulty.toString()

        binding.root.setOnClickListener { itemOnClickAction(recipe) }
    }
}
