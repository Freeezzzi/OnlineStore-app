package ru.freeezzzi.coursework.onlinestore.ui.mainpage.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.OrderStatusCardBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category

class CategoriesGridListAdapter() : ListAdapter<Category, CategoriesGridItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesGridItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OrderStatusCardBinding.inflate(layoutInflater, parent, false)
        return CategoriesGridItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesGridItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem
        }
    }
}