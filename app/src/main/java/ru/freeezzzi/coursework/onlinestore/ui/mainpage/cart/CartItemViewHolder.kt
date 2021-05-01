package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CartListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class CartItemViewHolder(
    private val binding: CartListItemBinding
) : RecyclerView.ViewHolder(binding.root){
    private var thisProduct: Product? = null

    fun onBind(
        product: Product,
        itemOnClickAction: (Product) -> Unit,
        addItemAction: (Product) -> Unit,
        removeItemAction: (Product) -> Unit
    ){
        setPicture(product)
        thisProduct = product
        fillData(thisProduct!!)
        binding.root.setOnClickListener {
            itemOnClickAction(thisProduct!!)
        }
        binding.cartListAdd.setOnClickListener {
            addItemAction(thisProduct!!)
            binding.cartListCount.text = thisProduct!!.countInCart.toString()
        }
        binding.cartListRemove.setOnClickListener {
            removeItemAction(thisProduct!!)
            binding.cartListCount.text = thisProduct!!.countInCart.toString()
        }
    }

    private fun fillData(product: Product) {
        setPicture(product)
        binding.cartListLabel.text = product.title
        binding.cartListPrice.text = product.price.toPrice()
        binding.cartListWeight.text = product.weight
        binding.cartListCount.text = thisProduct!!.countInCart.toString()
    }

    private fun setPicture(product: Product) {
        if (product.imageUrl.isEmpty()) {
            binding.cartListImage.setImageResource(R.color.white)
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
                .into(binding.cartListImage)
        }
    }
}
