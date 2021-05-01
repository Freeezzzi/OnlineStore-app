package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.ProductItemGridBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

class SalesListAdapter(
    private val itemOnClickAction: (Product) -> Unit,
    private val addItemAction: (Product) -> Unit,
    private val removeItemAction: (Product) -> Unit
) : ListAdapter<Product, SalesItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemGridBinding.inflate(layoutInflater, parent, false)
        return SalesItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalesItemViewHolder, position: Int) {
        holder.onBind(
            product = getItem(position),
            itemOnClickAction = itemOnClickAction,
            addItemAction = addItemAction,
            removeItemAction = removeItemAction
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem && oldItem.countInCart == newItem.countInCart
        }
    }
}
