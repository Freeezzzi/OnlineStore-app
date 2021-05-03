package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.selection.SelectionTracker
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.PaymentFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class PaymentFragment : BaseFragment(R.layout.payment_fragment) {
    private val binding: PaymentFragmentBinding by viewBinding(PaymentFragmentBinding::bind)

    private val viewModel: CheckoutViewModel by activityViewModels()


    override fun initViews(view: View) {
        super.initViews(view)

        var sum = 0
        viewModel.productsList.forEach {
            sum += it.countInCart * it.price.toInt()
        }
        binding.paymentTotal.text = sum.toString().toPrice()
        binding.paymentMethodBalance.text = getString(R.string.payment_method_balance, viewModel.user.balance.toString().toPrice())
        binding.paymentPayButton.setOnClickListener {
            viewModel.placeOrder()
        }

        viewModel.orderStatus.observe(viewLifecycleOwner, ::orderStatus)

        binding.paymentToolbar.toolbarTitle.text = getString(R.string.payment)
        binding.paymentToolbar.toolbarBackButton.setOnClickListener {
            val action = CheckoutFragmentDirections.actionCheckoutFragmentToEditAddressFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    fun orderStatus(newValue: ViewState<Boolean, String?>) {
        when (newValue) {
            is ViewState.Success -> binding.paymentMethodBalance.text = "0"
            is ViewState.Loading -> binding.paymentMethodBalance.text = "2" // TODO loading
            is ViewState.Error -> Toast.makeText(requireContext(), newValue.result, Toast.LENGTH_LONG).show()
        }
    }
}
