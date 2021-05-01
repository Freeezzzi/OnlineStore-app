package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.ProductItemGridBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SalesItemViewHolder(
    private val binding: ProductItemGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        product: Product,
        itemOnClickAction: (Product) -> Unit
    ) {
        fillData(product)
        binding.root.setOnClickListener {
            itemOnClickAction(product)
        }
    }

    private fun fillData(product: Product) {
        setPicture(product)
        binding.gridItemName.text = product.title
        binding.gridItemPrice.text = product.price
        binding.gridItemWeight.text = product.weight// TODO Добавить вес продукта
    }

    private fun setPicture(product: Product) {
        if (product.imageUrl.isEmpty()) {
            binding.gridItemImage.setImageResource(R.color.white)
        } else {
            Picasso.get().isLoggingEnabled = true
            Picasso.get()
                .load(product.imageUrl)
                .placeholder(R.color.white)
                .error(R.color.white)
                // .transform(transformation)
                .fit()
                .centerInside()
                // .centerCrop()
                .into(binding.gridItemImage)
        }
    }
}
