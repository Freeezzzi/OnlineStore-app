package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.constraintlayout.solver.state.Dimension
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.TimePickerCardBinding

class TimePickerItemViewHolder(
    private val binding: TimePickerCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        time: String,
        isSelected: Boolean
    ) {
        binding.timePickerValue.text = time
        binding.root.isActivated = isSelected
        if(isSelected){ //выбран
            binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.pale_orange))
            binding.root.strokeColor = binding.root.resources.getColor(R.color.orange)
            binding.root.strokeWidth = 4

            binding.timePickerValue.setTextColor(binding.root.resources.getColor(R.color.black_custom))
        }
        else{
            binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.white))
            binding.root.strokeColor = binding.root.resources.getColor(R.color.black_custom)
            binding.root.strokeWidth = 0

            binding.timePickerValue.setTextColor(binding.root.resources.getColor(R.color.gray))
        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }
}
