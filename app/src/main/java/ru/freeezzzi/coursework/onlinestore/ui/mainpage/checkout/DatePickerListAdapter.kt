package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.DatePickerCardBinding

class DatePickerListAdapter() : ListAdapter<String, DatePickerItemViewHolder>(DIFF_CALLBACK) {
    init {
        setHasStableIds(true)
    }
    var tracker: SelectionTracker<Long>? = null
    var count = 0 // показывает весь ли лист уже отображается

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatePickerItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DatePickerCardBinding.inflate(layoutInflater, parent, false)
        return DatePickerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatePickerItemViewHolder, position: Int) {
        tracker?.let {
            if (count >= currentList.size && tracker!!.selection.size() == 0) {
                tracker!!.select(position.toLong())
            }
            holder.onBind(getItem(position), it.isSelected(position.toLong()))
            count++
            holder.onBind(getItem(position), it.isSelected(position.toLong()))
            holder.itemView.setOnClickListener {
                tracker!!.clearSelection()
                tracker!!.select(position.toLong())
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    fun resetCount() { count = 0 }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}

class DateDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as DatePickerItemViewHolder)
                .getItemDetails()
        }
        return null
    }
}
