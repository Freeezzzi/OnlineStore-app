package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile.singleorder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.CartListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.setPicture
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SingleOrderItemViewHolder(
    private val binding: CartListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var thisProduct: Product? = null

    fun onBind(
        product: Product,
        itemOnClickAction: (Product) -> Unit
    ) {
        thisProduct = product
        fillData(thisProduct!!)
        binding.root.setOnClickListener {
            itemOnClickAction(thisProduct!!)
        }
        binding.cartListAdd.visibility = View.INVISIBLE
        binding.cartListRemove.visibility = View.INVISIBLE
    }

    private fun fillData(product: Product) {
        binding.cartListImage.setPicture(product.imageUrl)
        binding.cartListLabel.text = product.title
        binding.cartListPrice.text = product.price.toPrice()
        binding.cartListWeight.text = product.weight
        binding.cartListCount.text = "x ${product.countInCart}"
    }
}