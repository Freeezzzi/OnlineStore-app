package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.SalesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class SalesFragment : BaseFragment(R.layout.sales_fragment) {

    private val binding by viewBinding(SalesFragmentBinding::bind)

    private val args: SalesFragmentArgs by navArgs()

    override fun initViews(view: View) {
        super.initViews(view)

        binding.salesIncluded.salesToolbarLabel.text = args.categoryName.title
        binding.salesIncluded.salesToolbarArrowback.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        fillChipGroup()
    }

    fun fillChipGroup() {
        val chip = Chip(context)
        chip.setChipBackgroundColorResource(R.color.white_grey)
        chip.setEnsureMinTouchTargetSize(false)
        chip.setText(getString(R.string.all))
        chip.setOnClickListener {
            // chipClickListener((it as TextView).text.toString()) }
        }
        binding.salesFragmentChipgroup.addView(chip)
        val list = args.categoryName.relatedCategories.split(";")
        for (item in list) {
            val chip = Chip(context)
            chip.setChipBackgroundColorResource(R.color.white_grey)
            chip.setEnsureMinTouchTargetSize(false)
            chip.setText(item)
            chip.setOnClickListener {
                // chipClickListener((it as TextView).text.toString()) }
            }
            binding.salesFragmentChipgroup.addView(chip)
        }
    }
}
