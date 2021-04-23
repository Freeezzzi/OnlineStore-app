package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.CategoryListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category

class CategoriesListAdapter() : ListAdapter<Category, CategoriesItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryListItemBinding.inflate(layoutInflater, parent, false)
        return CategoriesItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesItemViewHolder, position: Int) {
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
