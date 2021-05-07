package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.SalesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerSalesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.home.HomeViewModel
import ru.freeezzzi.coursework.onlinestore.ui.setPicture
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SalesFragment : BaseFragment(R.layout.sales_fragment) {

    private val binding by viewBinding(SalesFragmentBinding::bind)

    private val args: SalesFragmentArgs by navArgs()

    private val homeViewModel : HomeViewModel by activityViewModels()

    private val listAdapter = SalesListAdapter(
        itemOnClickAction = ::productClicked,
        addItemAction = { cartViewModel.addOneItem(it) },
        removeItemAction = { cartViewModel.removeOneItem(it) }
    )

    private val viewModel: SalesViewModel by viewModels(
        factoryProducer = { SalesViewModelFactory() }
    )

    private val cartViewModel: CartViewModel by activityViewModels()

    override fun initViews(view: View) {
        super.initViews(view)

        binding.salesSwipeRefreshLayout.setOnRefreshListener { viewModel.getProductsByCategory(args.categoryName.id) }

        // recyclerview
        binding.salesFragmentRecyclerview.adapter = listAdapter
        binding.salesFragmentRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        // toolbar label
        binding.salesIncluded.salesToolbarLabel.text = args.categoryName.title
        binding.salesIncluded.salesToolbarArrowback.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        viewModel.productsList.observe(viewLifecycleOwner, ::productsChanged)
        viewModel.getProductsByCategory(args.categoryName.id)

        // bootm sheet dialog
        val bts = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
        bts.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun productsChanged(newValue: ViewState<List<Product>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                newValue.result.forEach { cartViewModel.isInCart(it) }
                listAdapter.submitList(newValue.result.toMutableList())
                binding.salesSwipeRefreshLayout.isRefreshing = false
                fillChipGroup()
            }
            is ViewState.Loading -> binding.salesSwipeRefreshLayout.isRefreshing = true
            is ViewState.Error -> {
                // TODO вывести ошибку
                binding.salesSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    /**
     * Заполняется и открывается Bottom sheet dialog
     */
    private fun productClicked(product: Product) {
        homeViewModel.saveToRecentlyWathced(product)
        binding.salesBottomSheet.productSheetImage.setPicture(product.imageUrl)
        binding.salesBottomSheet.also {
            it.productSheetLabel.text = product.title
            it.productSheetPrice.text = product.price.toPrice()
            it.productSheetWeight.text = product.weight
            it.productSheetCountryValue.text = product.country
            it.productSheetBrandValue.text = product.brand
            it.productSheetAmountValue.text = product.amount.toString()
            setUpAddToCartButtonBTD(product)

            it.bottomSheetInclude.also {
                // Нажатие на левую сторону кнопки
                it.addButtonLeftside.setOnClickListener {
                    if (product.countInCart > 0) {
                        cartViewModel.removeOneItem(product)
                        setUpAddToCartButtonBTD(product)
                        listAdapter.notifyDataSetChanged()
                    } else {
                        cartViewModel.addOneItem(product)
                        setUpAddToCartButtonBTD(product)
                        listAdapter.notifyDataSetChanged()
                    }
                }
                // нажатие на правую сторону кнопки
                it.addButtonRightSide.setOnClickListener {
                    cartViewModel.addOneItem(product)
                    setUpAddToCartButtonBTD(product)
                    listAdapter.notifyDataSetChanged()
                }
            }
        }

        val btd = BottomSheetBehavior.from(binding.salesBottomSheet.productBottomSheetRoot)
        btd.state = BottomSheetBehavior.STATE_EXPANDED
        btd.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bg.setVisibility(View.GONE)
                    binding.salesIncluded.bgToolbar.visibility = View.GONE
                    listAdapter.notifyDataSetChanged()
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

    fun setUpAddToCartButtonBTD(product: Product) {
        if (product.countInCart > 0) {
            binding.salesBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.VISIBLE
                it.addToCartButtonDelete.visibility = View.VISIBLE
                it.addToCartButtonCount.visibility = View.VISIBLE
                it.addToCartButtonLabel.visibility = View.INVISIBLE
                it.imageView4.visibility = View.INVISIBLE
                it.addToCartButtonCount.text = product.countInCart.toString()
            }
        } else {
            binding.salesBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.INVISIBLE
                it.addToCartButtonDelete.visibility = View.INVISIBLE
                it.addToCartButtonCount.visibility = View.INVISIBLE
                it.addToCartButtonLabel.visibility = View.VISIBLE
                it.imageView4.visibility = View.VISIBLE
            }
        }
    }

    /**
     *  UI
     */
    fun fillChipGroup() {
        // TODO clear chips
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
}

private class SalesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerSalesViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
