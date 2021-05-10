package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeCardBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe

class RecipesListAdapter(
    private val itemClickAction: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipesItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipeCardBinding.inflate(layoutInflater, parent, false)
        return RecipesItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesItemViewHolder, position: Int) {
        holder.onBind(getItem(position), itemClickAction)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}
