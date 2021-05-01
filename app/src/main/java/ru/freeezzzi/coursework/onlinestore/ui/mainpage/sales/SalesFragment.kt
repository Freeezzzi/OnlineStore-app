package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.SalesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerSalesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SalesFragment : BaseFragment(R.layout.sales_fragment) {

    private val binding by viewBinding(SalesFragmentBinding::bind)

    private val args: SalesFragmentArgs by navArgs()

    private val listAdapter = SalesListAdapter(
        itemOnClickAction = ::productClicked
    )

    private val viewModel: SalesViewModel by viewModels(
        factoryProducer = { SalesViewModelFactory() }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        // recyclerview
        binding.salesFragmentRecyclerview.adapter = listAdapter
        binding.salesFragmentRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        // toolbar label
        binding.salesIncluded.salesToolbarLabel.text = args.categoryName.title
        binding.salesIncluded.salesToolbarArrowback.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.salesFragmentLabel.text = args.categoryName.title // TODO менять в зависимости от категории
        viewModel.productsList.observe(viewLifecycleOwner, ::productsChanged)
        viewModel.getProductsByCategory(args.categoryName.id)

        // bootm sheet dialog
        val bts = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
        bts.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun productsChanged(newValue: ViewState<List<Product>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                listAdapter.submitList(newValue.result.toMutableList())
                listAdapter.itemCount
                fillChipGroup()
            }
            // is ViewState.Loading -> //TODO загрузка
            // is ViewState.Error -> //TODO вывести ошибку
        }
    }

    /**
     * Заполняется и открывается Bottom sheet dialog
     */
    private fun productClicked(product: Product) {
        setPicture(product, binding.salesBottomSheet.productSheetImage)
        binding.salesBottomSheet.also {
            it.productSheetLabel.text = product.title
            it.productSheetPrice.text = product.price.toPrice()
            it.productSheetWeight.text = product.weight
        }

        val bts = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
        bts.state = BottomSheetBehavior.STATE_EXPANDED
        bts.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bg.setVisibility(View.GONE)
                    binding.salesIncluded.bgToolbar.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bg.visibility = View.VISIBLE
                binding.bg.alpha = slideOffset
                binding.salesIncluded.bgToolbar.visibility = View.VISIBLE
                binding.salesIncluded.bgToolbar.alpha = slideOffset
            }
        })
    }

    /**
     *  UI
     */
    fun fillChipGroup() {
        val chip = Chip(context)
        chip.setChipBackgroundColorResource(R.color.white)
        chip.setEnsureMinTouchTargetSize(false)
        chip.setText(getString(R.string.all))
        chip.setOnClickListener {
            // chipClickListener((it as TextView).text.toString()) }
        }
        binding.salesFragmentChipgroup.addView(chip)
        /*val list = args.categoryName.relatedCategories.split(";")
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
        }*/
    }

    private fun setPicture(product: Product, imageView: ImageView) {
        if (product.imageUrl.isEmpty()) {
            imageView.setImageResource(R.color.white)
        } else {
            Picasso.get().isLoggingEnabled = true
            Picasso.get()
                .load(product.imageUrl)
                .placeholder(R.color.white)
                .error(R.color.white)
                // .transform(transformation)
                .fit()
                .centerInside()
                // .centerCrop()
                .into(imageView)
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
