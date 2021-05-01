package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.ProductItemGridBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SalesItemViewHolder(
    private val binding: ProductItemGridBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var thisProduct: Product? = null

    fun onBind(
        product: Product,
        itemOnClickAction: (Product) -> Unit,
        addItemAction: (Product) -> Unit,
        removeItemAction: (Product) -> Unit
    ) {
        thisProduct = product
        fillData(thisProduct!!)
        binding.root.setOnClickListener {
            itemOnClickAction(thisProduct!!)
        }
        binding.gridItemAdd.setOnClickListener {
            addItemAction(thisProduct!!)
            setUpButtons()
        }
        binding.gridItemDelete.setOnClickListener {
            removeItemAction(thisProduct!!)
            setUpButtons()
        }
        binding.gridItemCart.setOnClickListener {
            addItemAction(thisProduct!!)
            setUpButtons()
        }
    }

    private fun fillData(product: Product) {
        setPicture(product)
        binding.gridItemName.text = product.title
        binding.gridItemPrice.text = product.price.toPrice()
        binding.gridItemWeight.text = product.weight
        setUpButtons()
    }

    private fun setUpButtons(){
        if (thisProduct!!.countInCart > 0) {
            binding.gridItemCart.visibility = View.INVISIBLE
            binding.gridItemAdd.visibility = View.VISIBLE
            binding.gridItemDelete.visibility = View.VISIBLE
            binding.gridItemCount.visibility = View.VISIBLE
            binding.gridItemCount.text = thisProduct!!.countInCart.toString()
        } else {
            binding.gridItemCart.visibility = View.VISIBLE
            binding.gridItemAdd.visibility = View.INVISIBLE
            binding.gridItemDelete.visibility = View.INVISIBLE
            binding.gridItemCount.visibility = View.INVISIBLE
        }
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
