package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import okhttp3.internal.immutableListOf
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CheckoutFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.toPrice
import java.text.SimpleDateFormat
import java.util.*

class CheckoutFragment : BaseFragment(R.layout.checkout_fragment) {
    private val binding: CheckoutFragmentBinding by viewBinding(CheckoutFragmentBinding::bind)

    private val viewModel: CheckoutViewModel by activityViewModels()

    private val listAdapter = CheckoutListAdapter()

    private val timeListAdapter = TimePickerListAdapter()
    private var timeListTracker: SelectionTracker<Long>? = null

    private val dateListAdapter = DatePickerListAdapter()
    private var dateListTracker: SelectionTracker<Long>? = null

    private val deliveryTime = immutableListOf<String>(
        "11:00-15:00",
        "15:00-19:00",
        "19:00-21:00"
    )

    override fun initViews(view: View) {
        super.initViews(view)

        binding.recyclerView2.adapter = listAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Для корректного выбоар даты и времени обнулим счетчик
        timeListAdapter.resetCount()
        dateListAdapter.resetCount()

        setUpAddressCard()
        setUpValues()
        setUpDateAndTime()
        setUpClickListeners()
        listAdapter.submitList(viewModel.productsList)

        // BTD
        binding.dateTimeBottomSheet.timeRecyclerView.adapter = timeListAdapter
        binding.dateTimeBottomSheet.timeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        timeListTracker = SelectionTracker.Builder<Long>(
            "time_picking",
            binding.dateTimeBottomSheet.timeRecyclerView,
            StableIdKeyProvider(binding.dateTimeBottomSheet.timeRecyclerView),
            TimeDetailsLookup(binding.dateTimeBottomSheet.timeRecyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything() // Можно переопределить и самому настроить поведение(вернуть true - можно выбрать, false - нельзя)
        ).build()
        timeListTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (!timeListTracker!!.selection.isEmpty) {
                        viewModel.deliveryTime = timeListAdapter.currentList[timeListTracker!!.selection.elementAt(0).toInt()]
                    } else {
                        viewModel.deliveryTime = ""
                    }
                }
            }
        )
        timeListAdapter.tracker = timeListTracker
        timeListAdapter.submitList(deliveryTime)

        binding.dateTimeBottomSheet.dateRecyclerView.adapter = dateListAdapter
        binding.dateTimeBottomSheet.dateRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dateListTracker = SelectionTracker.Builder<Long>(
            "date_picking",
            binding.dateTimeBottomSheet.dateRecyclerView,
            StableIdKeyProvider(binding.dateTimeBottomSheet.dateRecyclerView),
            DateDetailsLookup(binding.dateTimeBottomSheet.dateRecyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything() // Можно переопределить и самому настроить поведение(вернуть true - можно выбрать, false - нельзя)
        ).build()
        dateListTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (!dateListTracker!!.selection.isEmpty) {
                        viewModel.deliveryDate = dateListAdapter.currentList[dateListTracker!!.selection.elementAt(0).toInt()]
                    } else {
                        viewModel.deliveryDate = ""
                    }
                }
            }
        )
        dateListAdapter.tracker = dateListTracker
        dateListAdapter.submitList(getDeliveryDatesList())
    }

    fun setUpValues() {
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
        binding.checkoutTotalValue.text = (subtotal + deliveryfee).toString().toPrice() // TODO вычесть скидку купона скидку купона
        binding.checkoutToolbar.toolbarTitle.text = getString(R.string.checkout)
    }

    fun setUpClickListeners() {
        binding.checkoutToolbar.toolbarBackButton.setOnClickListener {
            // TODO вернуться из активности
            requireActivity().finish()
        }
        binding.checkoutAddressCard.root.setOnClickListener {
            val action = CheckoutFragmentDirections.actionCheckoutFragmentToEditAddressFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.checkoutTimeCard.setOnClickListener {
            openBTD()
        }
        binding.checkoutCheckoutButton.setOnClickListener {
            if (viewModel.deliveryTime.isNotBlank() &&
                viewModel.deliveryDate.isNotBlank() &&
                viewModel.user.address != null
            ) {
                val action = CheckoutFragmentDirections.actionCheckoutFragmentToPaymentFragment()
                Navigation.findNavController(binding.root).navigate(action)
            } else {
                // TODO show error
            }
        }
    }

    fun setUpAddressCard() {
        if (viewModel.user.address == null) {
            binding.checkoutAddressCard.addressCardLabel.text = getString(R.string.choose_address)
            binding.checkoutAddressCard.addressCartAddress.visibility = View.INVISIBLE
            binding.checkoutAddressCard.addressCartName.visibility = View.INVISIBLE
            return
        }
        binding.checkoutAddressCard.let {
            it.addressCartAddress.visibility = View.VISIBLE
            it.addressCartName.visibility = View.VISIBLE
            it.addressCardLabel.text = getString(R.string.default_address_type)
            it.addressCartAddress.text = viewModel.user.address!!.toStringWithoutPhoneAndName()
            it.addressCartName.text = viewModel.user.address!!.toStringPhoneName()
        }
    }

    fun setUpDateAndTime(){
        if (viewModel.deliveryDate.isNotBlank() && viewModel.deliveryTime.isNotBlank()){
            binding.checkoutTimeCardTitle.text = viewModel.deliveryDate + " " + viewModel.deliveryTime
        }
    }

    fun openBTD() {
        val btd = BottomSheetBehavior.from(binding.dateTimeBottomSheet.root)
        btd.state = BottomSheetBehavior.STATE_EXPANDED
        btd.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bgCheckout.setVisibility(View.GONE)
                    binding.checkoutToolbar.bgToolbarCheckout.visibility = View.GONE
                    listAdapter.notifyDataSetChanged()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bgCheckout.visibility = View.VISIBLE
                binding.bgCheckout.alpha = slideOffset
                binding.checkoutToolbar.bgToolbarCheckout.visibility = View.VISIBLE
                binding.checkoutToolbar.bgToolbarCheckout.alpha = slideOffset
            }
        })
        binding.dateTimeBottomSheet.dateTimeSaveButton.setOnClickListener {
            if (viewModel.deliveryTime.isNotBlank() && viewModel.deliveryDate.isNotBlank()) {
                binding.checkoutTimeCardTitle.text = viewModel.deliveryDate + " " + viewModel.deliveryTime
            }
            btd.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun getDeliveryDatesList(): List<String> {
        val dates = mutableListOf<String>()
        var time = Calendar.getInstance().timeInMillis
        for (i in 0 until 7) {
            time += ONE_DAY
            val formatted = SimpleDateFormat("EEEE d MMM").format(time)
            dates.add(formatted)
        }
        return dates
    }

    companion object {
        const val ONE_DAY = 1000L * 60 * 60 * 24
    }
}
