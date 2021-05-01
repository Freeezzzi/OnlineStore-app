package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CartFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCartViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
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
        // TODO Обновлять счетчик внутри фрагмента(обновить сумму, кол-во предметов)
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
        // TODO открывать BTD
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
