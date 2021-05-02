package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CategoryListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class CategoriesItemViewHolder(
    private val binding: CategoryListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val transformation: Transformation
    init {
        val dimension = itemView.resources.getDimension(R.dimen.rounded_corner_rad)
        val cornerRadius = dimension.toInt()
        transformation = RoundedCornersTransformation(cornerRadius, 0)
    }
    fun bind(item: Category) {
        binding.categoryItemLabel.text = item.title
        binding.categoryItemImage.setPicture(item.imageUrl)
        val action = CategoriesFragmentDirections.actionOpenSalesFromCategory(item)
        binding.root.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}
