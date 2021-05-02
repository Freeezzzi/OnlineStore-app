package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.CheckoutListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

class CheckoutListAdapter(): ListAdapter<Product, CheckoutProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CheckoutListItemBinding.inflate(layoutInflater, parent, false)
        return CheckoutProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutProductViewHolder, position: Int) {
        holder.onBind(getItem(position))
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