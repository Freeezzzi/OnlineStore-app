package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.TimePickerCardBinding

class TimePickerListAdapter() : ListAdapter<String, TimePickerItemViewHolder>(DIFF_CALLBACK) {
    init {
        setHasStableIds(true)
    }
    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimePickerItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TimePickerCardBinding.inflate(layoutInflater, parent, false)
        return TimePickerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimePickerItemViewHolder, position: Int) {
        tracker?.let {
            holder.onBind(getItem(position), it.isSelected(position.toLong()))
            holder.itemView.setOnClickListener {
                tracker!!.clearSelection()
                tracker!!.select(position.toLong())
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}

class TimeDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as TimePickerItemViewHolder)
                .getItemDetails()
        }
        return null
    }
}
