package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.singlerecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeStepCardItemBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Step

class StepsListAdapter() : ListAdapter<Step, StepsItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepCardItemBinding.inflate(layoutInflater, parent, false)
        return StepsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepsItemViewHolder, position: Int) {
        holder.onBind(position, getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
                oldItem.stepInfo == newItem.stepInfo

            override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
                oldItem == newItem
        }
    }
}
