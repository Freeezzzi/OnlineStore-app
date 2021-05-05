package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile.singleorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.CartListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

class SingleOrderListAdapter(
    private val itemOnClickAction: (Product) -> Unit
) : ListAdapter<Product, SingleOrderItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleOrderItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CartListItemBinding.inflate(layoutInflater, parent, false)
        return SingleOrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleOrderItemViewHolder, position: Int) {
        holder.onBind(
            product = getItem(position),
            itemOnClickAction = itemOnClickAction
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}