package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.singlerecipe

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeIngredientItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Ingredient
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

// не заюыть про notifydata set changed во фрагменте
class IngredientsItemViewHolder(
    private val binding: RecipeIngredientItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var thisProduct: Product? = null

    fun onBind(
        ingredient: Ingredient,
        itemOnClickAction: (Product) -> Unit,
        addItemAction: (Product) -> Unit,
        removeItemAction: (Product) -> Unit
    ) {
        thisProduct = ingredient.product
        binding.ingredientName.text = ingredient.name
        binding.imgredientWeight.text = ingredient.count
        setUpAddToCartButtonBTD(thisProduct!!)

        binding.root.setOnClickListener {
            itemOnClickAction(thisProduct!!)
        }
        binding.ingredientCartButton.setOnClickListener {
            addItemAction(thisProduct!!)
            setUpAddToCartButtonBTD(thisProduct!!)
        }
        binding.also {
            // Нажатие на левую сторону кнопки
            it.addButtonLeftside.setOnClickListener {
                if (thisProduct!!.countInCart > 0) {
                    removeItemAction(thisProduct!!)
                    setUpAddToCartButtonBTD(thisProduct!!)
                } else {
                    addItemAction(thisProduct!!)
                    setUpAddToCartButtonBTD(thisProduct!!)
                }
            }
            // нажатие на правую сторону кнопки
            it.addButtonRightSide.setOnClickListener {
                addItemAction(thisProduct!!)
                setUpAddToCartButtonBTD(thisProduct!!)
            }
        }
    }

    fun setUpAddToCartButtonBTD(product: Product) {
        if (product.countInCart > 0) {
            binding.also {
                it.ingredientButton.visibility = View.VISIBLE
                it.addToCartButtonAdd.visibility = View.VISIBLE
                it.addToCartButtonDelete.visibility = View.VISIBLE
                it.addToCartButtonCount.visibility = View.VISIBLE
                it.addToCartButtonLabel.visibility = View.INVISIBLE
                it.addToCartButtonCount.text = product.countInCart.toString()
            }
            binding.ingredientCartButton.visibility = View.INVISIBLE
        } else {
            binding.ingredientButton.visibility = View.INVISIBLE
            binding.ingredientCartButton.visibility = View.VISIBLE
        }
    }
}
