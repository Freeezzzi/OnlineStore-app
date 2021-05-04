package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.OrdersListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout.CheckoutListAdapter
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class OrdersItemViewHolder(
    private val binding: OrdersListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val listAdapter = CheckoutListAdapter()

    fun onBind(
        order: Order,
        itemClickAction: (Order) -> Unit
    ) {
        binding.root.setOnClickListener {
            itemClickAction(order)
        }
        binding.orderItemRecyclerview.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.orderItemRecyclerview.adapter = listAdapter
        listAdapter.submitList(order.products)

        // status
        if (order.status == Order.STATUS_PLACED) {
            binding.orderItemStatus.text = binding.root.resources.getString(R.string.placed)
            binding.orderItemStatus.setTextColor(binding.root.resources.getColor(R.color.stroke_dark_orange))
        }
        if (order.status == Order.STATUS_PREPARING) {
            binding.orderItemStatus.text = binding.root.resources.getString(R.string.preparing)
            binding.orderItemStatus.setTextColor(binding.root.resources.getColor(R.color.stroke_yellow))
        }
        if (order.status == Order.STATUS_ONTHEWAY) {
            binding.orderItemStatus.text = binding.root.resources.getString(R.string.on_the_way)
            binding.orderItemStatus.setTextColor(binding.root.resources.getColor(R.color.stroke_blue))
        }
        if (order.status == Order.STATUS_DELIVERED) {
            binding.orderItemStatus.text = binding.root.resources.getString(R.string.delivered)
            binding.orderItemStatus.setTextColor(binding.root.resources.getColor(R.color.stroke_green))
        }

        binding.orderItemPlacedValue.text = order.orderTime
        var sum = 0
        order.products.forEach {
            sum += it.price.toInt() * it.countInCart
        }
        binding.orderItemCost.text = sum.toString().toPrice()
        binding.orderItemCount.text = order.products.size.toString()
    }
}
