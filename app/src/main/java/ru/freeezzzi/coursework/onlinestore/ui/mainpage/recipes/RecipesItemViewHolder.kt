package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.R
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
        var difficulty = ""
        if (recipe.difficulty == Recipe.DIFFICULTY_EASY) difficulty = binding.root.resources.getString(R.string.easy)
        if (recipe.difficulty == Recipe.DIFFICULTY_MEDIUM) difficulty = binding.root.resources.getString(R.string.medium)
        if (recipe.difficulty == Recipe.DIFFUCULTY_HARD) difficulty = binding.root.resources.getString(R.string.hard)
        binding.recipeCardDifficulty.text = difficulty

        binding.root.setOnClickListener { itemOnClickAction(recipe) }
    }
}
