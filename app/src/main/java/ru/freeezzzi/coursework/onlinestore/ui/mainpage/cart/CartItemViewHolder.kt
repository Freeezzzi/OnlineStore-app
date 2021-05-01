package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.CartListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

class CartItemViewHolder(
    private val binding: CartListItemBinding
) : RecyclerView.ViewHolder(binding.root){

    fun onBind(
            product: Product
    ){

    }
}
