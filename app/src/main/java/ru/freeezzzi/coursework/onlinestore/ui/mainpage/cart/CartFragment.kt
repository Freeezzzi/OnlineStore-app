package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import ru.freeezzzi.coursework.onlinestore.ui.MainActivity
import javax.inject.Inject

class CartFragment : BaseFragment(R.layout.cart_fragment) {

    private val binding by viewBinding(CartFragmentBinding::bind)

    private val viewModel: CartViewModel by activityViewModels()

    private val listAdapter = CartListAdapter()

    override fun initViews(view: View) {
        super.initViews(view)

        binding.cartRecyclerview.adapter = listAdapter
        binding.cartRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.cartList.observe(viewLifecycleOwner, ::cartListChanged)
    }

    fun cartListChanged(newValue: List<Product>) {
        listAdapter.submitList(newValue)
        // TODO Обновлять счетчик
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
