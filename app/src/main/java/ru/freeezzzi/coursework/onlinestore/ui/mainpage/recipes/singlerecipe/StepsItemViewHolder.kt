package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.singlerecipe

import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeStepCardItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Step
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class StepsItemViewHolder(
    private val binding: RecipeStepCardItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        position: Int,
        step: Step
    ) {
        binding.stepCardImage.setPicture(step.stepImage)
        binding.stepCardNumber.text = (position + 1).toString()
        binding.stepCardText.text = step.stepInfo
    }
}
