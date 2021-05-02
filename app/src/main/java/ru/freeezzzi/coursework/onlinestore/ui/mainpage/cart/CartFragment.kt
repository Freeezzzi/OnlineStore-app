package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CartFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCartViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.setPicture
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class CartFragment : BaseFragment(R.layout.cart_fragment) {

    private val binding by viewBinding(CartFragmentBinding::bind)

    private val cartViewModel: CartViewModel by activityViewModels()

    private val listAdapter = CartListAdapter(
        itemOnClickAction = ::productClicked,
        addItemAction = { cartViewModel.addOneItem(it) },
        removeItemAction = { cartViewModel.removeOneItem(it) }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        setUpUi()

        binding.cartRecyclerview.adapter = listAdapter
        binding.cartRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        cartViewModel.cartList.observe(viewLifecycleOwner, ::cartListChanged)
    }

    fun cartListChanged(newValue: List<Product>) {
        listAdapter.submitList(newValue)
        var subtotal = 0
        var itemCount = 0
        newValue.forEach {
            subtotal += it.price.toInt() * it.countInCart
            itemCount += it.countInCart
        }
        val deliveryfee = if (subtotal >= 700) 0 else 200

        binding.cartSubtotalValue.text = subtotal.toString().toPrice()
        binding.cartDeliveryfeeValue.text = deliveryfee.toString().toPrice()
        binding.cartItemcount.text = String.format(getString(R.string.you_have_n_items), itemCount)
        binding.cartTotalValue.text = (subtotal + deliveryfee).toString().toPrice()
    }

    private fun productClicked(product: Product) {
       binding.cartBottomSheet.productSheetImage.setPicture(product.imageUrl)
        binding.cartBottomSheet.also {
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

        val btd = BottomSheetBehavior.from(binding.cartBottomSheet.productBottomSheetRoot)
        btd.state = BottomSheetBehavior.STATE_EXPANDED
        btd.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bgCart.setVisibility(View.GONE)
                    binding.bgCartToolbar.visibility = View.GONE
                    listAdapter.notifyDataSetChanged()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bgCart.visibility = View.VISIBLE
                binding.bgCart.alpha = slideOffset
                binding.bgCartToolbar.visibility = View.VISIBLE
                binding.bgCartToolbar.alpha = slideOffset
            }
        })
    }

    fun setUpAddToCartButtonBTD(product: Product) {
        if (product.countInCart > 0) {
            binding.cartBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.VISIBLE
                it.addToCartButtonDelete.visibility = View.VISIBLE
                it.addToCartButtonCount.visibility = View.VISIBLE
                it.addToCartButtonLabel.visibility = View.INVISIBLE
                it.imageView4.visibility = View.INVISIBLE
                it.addToCartButtonCount.text = product.countInCart.toString()
            }
        } else {
            binding.cartBottomSheet.bottomSheetInclude.also {
                it.addToCartButtonAdd.visibility = View.INVISIBLE
                it.addToCartButtonDelete.visibility = View.INVISIBLE
                it.addToCartButtonCount.visibility = View.INVISIBLE
                it.addToCartButtonLabel.visibility = View.VISIBLE
                it.imageView4.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpUi() {
        binding.cartDeleteall.setOnClickListener { cartViewModel.initializeCart() }
        binding.cartCheckoutButton.setOnClickListener {
            // TODO checkout
        }
    }
}

class CartViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerCartViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
