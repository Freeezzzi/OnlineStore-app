package ru.freeezzzi.coursework.onlinestore.ui.mainpage.home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.HomeFragmentBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile.ProfileViewModel
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales.SalesListAdapter
import ru.freeezzzi.coursework.onlinestore.ui.setPicture
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class HomeFragment : BaseFragment(R.layout.home_fragment) {
    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)

    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val cartViewModel: CartViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val categoriesListAdapter = CategoriesGridListAdapter()

    private val recentlyWatchedListAdapter = SalesListAdapter(
        itemOnClickAction = ::openBSD,
        addItemAction = { cartViewModel.addOneItem(it) },
        removeItemAction = { cartViewModel.removeOneItem(it) }
    )

    private val onSaleListAdapter = SalesListAdapter(
        itemOnClickAction = ::openBSD,
        addItemAction = { cartViewModel.addOneItem(it) },
        removeItemAction = { cartViewModel.removeOneItem(it) }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        // observe
        homeViewModel.categoriesList.observe(viewLifecycleOwner, ::categoriesChanged)
        homeViewModel.recentlyWatched.observe(viewLifecycleOwner, ::recentlyWatchedChanged)
        homeViewModel.onSaleproductsList.observe(viewLifecycleOwner, ::onSaleChanged)

        if (profileViewModel.user.value!!.address == null) {
            binding.homeToolbar.homeLocationText.text = getString(R.string.set_address)
        } else {
            binding.homeToolbar.homeLocationText.text = profileViewModel.user.value!!.address!!.toStringWithoutPhoneAndName()
        }
        binding.homeToolbar.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileEditAddressFragment2()
            Navigation.findNavController(binding.root).navigate(action)
        }

        // recyclerviews
        binding.homeCategoriesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
        binding.homeCategoriesRecyclerview.adapter = categoriesListAdapter

        binding.homeSaleRecyclerview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.homeSaleRecyclerview.adapter = onSaleListAdapter

        binding.homeRecentlyRecyclerview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.homeRecentlyRecyclerview.adapter = recentlyWatchedListAdapter

        /*binding.homeSwipeRefreshLayout.setOnRefreshListener {
            homeViewModel.getCategories()
            homeViewModel.getProductsOnSale()
            homeViewModel.getRecentlyWathced()
        }*/

        //TODO добавить прослышивание обновления
        // TODO не забыть првоеряьбь есть ли айтем в корзине при загрузке и при переходе обратно в этот фрагмент (onBackpressed)
        homeViewModel.getCategories()
        homeViewModel.getProductsOnSale()
        homeViewModel.getRecentlyWathced()
    }

    private fun categoriesChanged(newValue: ViewState<List<Category>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                categoriesListAdapter.submitList(newValue.result)
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
            }
            is ViewState.Loading ->binding.homeSwipeRefreshLayout.visibility = View.VISIBLE
            is ViewState.Error -> {
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
                // TODO вывести ошибку
            }
        }
    }

    private fun recentlyWatchedChanged(newValue: ViewState<List<Product>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                newValue.result.forEach {
                    cartViewModel.isInCart(it)
                }
                recentlyWatchedListAdapter.submitList(newValue.result)
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
            }
            is ViewState.Loading -> binding.homeSwipeRefreshLayout.visibility = View.VISIBLE
            is ViewState.Error -> {
                // TODO вывести ошибку
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
            }
        }
    }

    private fun onSaleChanged(newValue: ViewState<List<Product>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                newValue.result.forEach {
                    cartViewModel.isInCart(it)
                }
                onSaleListAdapter.submitList(newValue.result)
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
            }
            is ViewState.Loading -> binding.homeSwipeRefreshLayout.visibility = View.VISIBLE
            is ViewState.Error -> {
                // TODO вывести ошибку
                binding.homeSwipeRefreshLayout.visibility = View.INVISIBLE
            }
        }
    }

    private fun openBSD(product: Product) {
        binding.homeBottomSheet.productSheetImage.setPicture(product.imageUrl)
        binding.homeBottomSheet.also {
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
                        recentlyWatchedListAdapter.notifyDataSetChanged()
                        onSaleListAdapter.notifyDataSetChanged()
                    } else {
                        cartViewModel.addOneItem(product)
                        setUpAddToCartButtonBTD(product)
                        recentlyWatchedListAdapter.notifyDataSetChanged()
                        onSaleListAdapter.notifyDataSetChanged()
                    }
                }
                // нажатие на правую сторону кнопки
                it.addButtonRightSide.setOnClickListener {
                    cartViewModel.addOneItem(product)
                    setUpAddToCartButtonBTD(product)
                    recentlyWatchedListAdapter.notifyDataSetChanged()
                    onSaleListAdapter.notifyDataSetChanged()
                }
            }
        }

        val btd = BottomSheetBehavior.from(binding.homeBottomSheet.productBottomSheetRoot)
        btd.state = BottomSheetBehavior.STATE_EXPANDED
        btd.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    homeViewModel.saveToRecentlyWathced(product)
                    binding.bgHome.setVisibility(View.GONE)
                    binding.bgToolbarHome.visibility = View.GONE
                    onSaleListAdapter.notifyDataSetChanged()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bgHome.visibility = View.VISIBLE
                binding.bgHome.alpha = slideOffset
                binding.bgToolbarHome.visibility = View.VISIBLE
                binding.bgToolbarHome.alpha = slideOffset
            }
        })
    }

    fun setUpAddToCartButtonBTD(product: Product) {
        if (product.countInCart > 0) {
            binding.homeBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.VISIBLE
                it.addToCartButtonDelete.visibility = View.VISIBLE
                it.addToCartButtonCount.visibility = View.VISIBLE
                it.addToCartButtonLabel.visibility = View.INVISIBLE
                it.imageView4.visibility = View.INVISIBLE
                it.addToCartButtonCount.text = product.countInCart.toString()
            }
        } else {
            binding.homeBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.INVISIBLE
                it.addToCartButtonDelete.visibility = View.INVISIBLE
                it.addToCartButtonCount.visibility = View.INVISIBLE
                it.addToCartButtonLabel.visibility = View.VISIBLE
                it.imageView4.visibility = View.VISIBLE
            }
        }
    }
}
