package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.SalesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerSalesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class SalesFragment : BaseFragment(R.layout.sales_fragment) {

    private val binding by viewBinding(SalesFragmentBinding::bind)

    private val args: SalesFragmentArgs by navArgs()

    private val viewModel: SalesViewModel by viewModels(
        factoryProducer = { SalesViewModelFactory() }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        binding.salesIncluded.salesToolbarLabel.text = args.categoryName.title
        binding.salesIncluded.salesToolbarArrowback.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        fillChipGroup()

        val bts = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
        bts.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun fillChipGroup() {
        val chip = Chip(context)
        chip.setChipBackgroundColorResource(R.color.white)
        chip.setEnsureMinTouchTargetSize(false)
        chip.setText(getString(R.string.all))
        chip.setOnClickListener {
            // chipClickListener((it as TextView).text.toString()) }
            val bts = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
            bts.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.salesFragmentChipgroup.addView(chip)
        val list = args.categoryName.relatedCategories.split(";")
        val set = list.toMutableSet()
        for (item in set) {
            val chip = Chip(context)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setEnsureMinTouchTargetSize(false)
            chip.setText(item)
            chip.setOnClickListener {
                // chipClickListener((it as TextView).text.toString()) }
            }
            binding.salesFragmentChipgroup.addView(chip)
        }
    }
}

private class SalesViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerSalesViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
