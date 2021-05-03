package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.DatePickerCardBinding

class DatePickerItemViewHolder(
    private val binding: DatePickerCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        time: String, // "day-of-week dd MMM"
        isSelected: Boolean
    ) {
        val splitedTime = time.split(" ")
        binding.dateDayofweek.text = splitedTime[0]
        binding.dateDate.text = splitedTime[1] + " " + splitedTime[2]
        binding.root.isActivated = isSelected
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }
}
