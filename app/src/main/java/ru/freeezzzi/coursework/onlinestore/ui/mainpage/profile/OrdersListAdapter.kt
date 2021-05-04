package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.OrdersListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Order

class OrdersListAdapter(
    private val itemClickAction: (Order) -> Unit
) : ListAdapter<Order, OrdersItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OrdersListItemBinding.inflate(layoutInflater, parent, false)
        return OrdersItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersItemViewHolder, position: Int) {
        holder.onBind(getItem(position), itemClickAction)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem == newItem
        }
    }
}
