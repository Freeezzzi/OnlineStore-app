package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.OrdersListFragmentBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState

class OrdersListFragment() : BaseFragment(R.layout.orders_list_fragment) {
    private val binding by viewBinding(OrdersListFragmentBinding::bind)

    private val viewModel: ProfileViewModel by activityViewModels()

    private val listAdapter = OrdersListAdapter(
        itemClickAction = {
            // TODO открывать другой фрагмент
        }
    )

    private val args: OrdersListFragmentArgs by navArgs()

    override fun initViews(view: View) {
        super.initViews(view)

        // TODO сделать запрос в зависимтости от аргумента

        binding.ordersFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersFragmentRecyclerview.adapter = listAdapter

        viewModel.ordersList.observe(viewLifecycleOwner, ::listChanged)

        fillChipGroup()
        binding.ordersToolbar.toolbarTitle.text = getString(R.string.orders)
        binding.ordersToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        binding.ordersSwipeRefreshLayout.setOnRefreshListener {
            // TODO запросить у viewModel данные в зависимости от чипа
        }
    }

    fun listChanged(newValue: ViewState<List<Order>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                binding.ordersSwipeRefreshLayout.isRefreshing = false
                listAdapter.submitList(newValue.result)
                binding.ordersFragmentCount.text = String.format(getString(R.string.orders_count), newValue.result.size)
            }
            is ViewState.Loading -> binding.ordersSwipeRefreshLayout.isRefreshing = true
            is ViewState.Error -> {
                binding.ordersSwipeRefreshLayout.isRefreshing = false
                listAdapter.submitList(newValue.oldvalue)
                binding.ordersFragmentCount.text = String.format(getString(R.string.orders_count), newValue.oldvalue.size)
                // TODO show error
            }
        }
    }

    fun fillChipGroup() {
        val stringRes = mutableListOf<Int>(R.string.all, R.string.placed, R.string.preparing, R.string.on_the_way, R.string.delivered)
        stringRes.forEach {
            val chip = Chip(context)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setEnsureMinTouchTargetSize(false)
            chip.setText(getString(it))
            chip.setOnClickListener {
                // TODO фильтровать значения
                // chip selected
            }
            binding.ordersFragmentChipgroup.addView(chip)
        }
    }
}
