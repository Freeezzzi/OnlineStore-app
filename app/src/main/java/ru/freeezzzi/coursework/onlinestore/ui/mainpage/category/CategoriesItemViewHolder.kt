package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CategoryListItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category

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
        setPicture(item)
        val action = CategoriesFragmentDirections.actionOpenSalesFromCategory(item)
        binding.root.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    private fun setPicture(category: Category) {
        if (category.imageUrl.isEmpty()) {
            binding.categoryItemImage.setImageResource(R.color.white)
        } else {
            Picasso.get().isLoggingEnabled = true
            Picasso.get()
                .load(category.imageUrl)
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(transformation)
                .fit()
                .centerInside()
                // .centerCrop()
                .into(binding.categoryItemImage)
        }
    }
}
