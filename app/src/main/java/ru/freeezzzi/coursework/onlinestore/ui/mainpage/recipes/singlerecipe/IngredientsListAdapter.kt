package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.singlerecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeIngredientItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Ingredient
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

class IngredientsListAdapter(
    private val itemOnClickAction: (Product) -> Unit,
    private val addItemAction: (Product) -> Unit,
    private val removeItemAction: (Product) -> Unit
) : ListAdapter<Ingredient, IngredientsItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipeIngredientItemBinding.inflate(layoutInflater, parent, false)
        return IngredientsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsItemViewHolder, position: Int) {
        holder.onBind(
            ingredient = getItem(position),
            itemOnClickAction = itemOnClickAction,
            addItemAction = addItemAction,
            removeItemAction = removeItemAction
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem.product.id == newItem.product.id

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem == newItem && oldItem.product.countInCart == newItem.product.countInCart
        }
    }
}