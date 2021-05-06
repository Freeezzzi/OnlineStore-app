package ru.freeezzzi.coursework.onlinestore.ui.mainpage.home

import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.OrderStatusCardBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class CategoriesGridItemViewHolder(
    private val binding: OrderStatusCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val transformation: Transformation
    init {
        val dimension = itemView.resources.getDimension(R.dimen.rounded_corner_rad)
        val cornerRadius = dimension.toInt()
        transformation = RoundedCornersTransformation(cornerRadius, 0)
    }
    fun bind(item: Category) {
        binding.orderStatusText.textSize = 14F
        binding.orderStatusText.text = item.title
        setPicture(item.imageUrl)
        val action = HomeFragmentDirections.actionHomeToSales(item)
        binding.root.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    fun setPicture(imageUrl: String) {
        if (imageUrl.isEmpty()) {
            binding.orderStatusImage.setImageResource(R.color.white)
        } else {
            Picasso.get().isLoggingEnabled = true
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(transformation)
                .fit()
                .centerInside()
                // .centerCrop()
                .into(binding.orderStatusImage)
        }
    }
}
