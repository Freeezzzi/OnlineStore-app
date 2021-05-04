package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.util.Log
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.R
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
        if (isSelected) { // выбран
            binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.pale_orange))
            binding.root.strokeColor = binding.root.resources.getColor(R.color.orange)
            binding.root.strokeWidth = 4

            binding.dateDate.setTextColor(binding.root.resources.getColor(R.color.black_custom))
            binding.dateDayofweek.setTextColor(binding.root.resources.getColor(R.color.orange))
        } else {
            binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.white))
            binding.root.strokeColor = binding.root.resources.getColor(R.color.black_custom)
            binding.root.strokeWidth = 0

            binding.dateDate.setTextColor(binding.root.resources.getColor(R.color.gray))
            binding.dateDayofweek.setTextColor(binding.root.resources.getColor(R.color.gray))
        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }
}
