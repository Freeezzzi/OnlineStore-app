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
        viewModel.getOrders(if (args.orderType == -1) Order.ORDERS_ALL else args.orderType)

        binding.ordersFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersFragmentRecyclerview.adapter = listAdapter

        viewModel.ordersList.observe(viewLifecycleOwner, ::listChanged)

        fillChipGroup()
        binding.ordersToolbar.toolbarTitle.text = getString(R.string.orders)
        binding.ordersToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        binding.ordersSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getOrders(getOrdersType())
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
            val chip = layoutInflater.inflate(R.layout.custom_chip, binding.ordersFragmentChipgroup, false) as Chip
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setEnsureMinTouchTargetSize(false)
            chip.setText(getString(it))
            chip.setOnClickListener {
                binding.ordersFragmentChipgroup.check(it.id)
            }
            binding.ordersFragmentChipgroup.addView(chip)
            setChipSelected(chip)
        }
        binding.ordersFragmentChipgroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.filterOrders(getOrdersType())
        }
    }

    fun getOrdersType(): Int {
        val id = binding.ordersFragmentChipgroup.checkedChipId
        val chip = binding.ordersFragmentChipgroup.findViewById<Chip>(id)
        if (chip.text.toString() == getString(R.string.all)) return Order.ORDERS_ALL
        if (chip.text.toString() == getString(R.string.placed)) return Order.STATUS_PLACED
        if (chip.text.toString() == getString(R.string.preparing)) return Order.STATUS_PREPARING
        if (chip.text.toString() == getString(R.string.on_the_way)) return Order.STATUS_ONTHEWAY
        if (chip.text.toString() == getString(R.string.delivered)) return Order.STATUS_DELIVERED
        return Order.ORDERS_ALL
    }

    fun setChipSelected(chip: Chip) {
        if (chip.text.toString() == getString(R.string.all) && args.orderType == Order.ORDERS_ALL) binding.ordersFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.placed) && args.orderType == Order.STATUS_PLACED) binding.ordersFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.preparing) && args.orderType == Order.STATUS_PREPARING) binding.ordersFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.on_the_way) && args.orderType == Order.STATUS_ONTHEWAY) binding.ordersFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.delivered) && args.orderType == Order.STATUS_DELIVERED) binding.ordersFragmentChipgroup.check(chip.id)
    }
}
