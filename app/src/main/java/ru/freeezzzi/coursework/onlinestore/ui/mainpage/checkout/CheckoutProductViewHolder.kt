package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.CheckoutListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class CheckoutProductViewHolder(
    private val binding: CheckoutListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(product: Product) {
        binding.checkoutListCount.text = "x${product.countInCart}"
        binding.checkoutListImage.setPicture(product.imageUrl)
    }
}
