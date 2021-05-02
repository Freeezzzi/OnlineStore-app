package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CheckoutFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.category.CategoriesFragmentDirections
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class CheckoutFragment : BaseFragment(R.layout.checkout_fragment) {
    private val binding: CheckoutFragmentBinding by viewBinding(CheckoutFragmentBinding::bind)

    private val viewModel: CheckoutViewModel by activityViewModels()

    private val listAdapter = CheckoutListAdapter()

    override fun initViews(view: View) {
        super.initViews(view)

        binding.recyclerView2.adapter = listAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        setUpValues()
        setUpClickListeners()
        listAdapter.submitList(viewModel.productsList)
    }

    fun setUpValues(){
        var subtotal = 0
        var itemCount = 0
        viewModel.productsList.forEach {
            subtotal += it.price.toInt() * it.countInCart
            itemCount += it.countInCart
        }
        val deliveryfee = if (subtotal >= 700) 0 else 200

        binding.checkoutCouponValue.text = 0.toString().toPrice()
        binding.checkoutSubtotalValue.text = subtotal.toString().toPrice()
        binding.checkoutDeliveryfeeValue.text = deliveryfee.toString().toPrice()
        binding.checkoutItemCount.text = String.format(getString(R.string.you_have_n_items), itemCount)
        binding.checkoutTotalValue.text = (subtotal + deliveryfee).toString().toPrice() //TODO вычесть скидку купона скидку купона
        binding.checkoutToolbar.toolbarTitle.text = getString(R.string.checkout)
    }

    fun setUpClickListeners(){
        binding.checkoutToolbar.toolbarBackButton.setOnClickListener {
            //TODO вернуться из активности
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.checkoutAddressCard.root.setOnClickListener {
            val action = CheckoutFragmentDirections.actionCheckoutFragmentToEditAddressFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}