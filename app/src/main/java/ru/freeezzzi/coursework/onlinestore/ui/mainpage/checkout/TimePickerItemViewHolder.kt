package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
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
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }
}
