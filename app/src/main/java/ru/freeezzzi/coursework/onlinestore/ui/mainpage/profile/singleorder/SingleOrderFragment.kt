package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile.singleorder

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.OrderFragmentBinding
import ru.freeezzzi.coursework.onlinestore.databinding.OrderStatusCardBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import ru.freeezzzi.coursework.onlinestore.ui.setPicture
import ru.freeezzzi.coursework.onlinestore.ui.toPrice

class SingleOrderFragment : BaseFragment(R.layout.order_fragment) {
    private val binding: OrderFragmentBinding by viewBinding(OrderFragmentBinding::bind)

    private val cartViewModel: CartViewModel by activityViewModels()

    private val args: SingleOrderFragmentArgs by navArgs()

    private val listAdapter = SingleOrderListAdapter(
        itemOnClickAction = ::productClicked
    )

    override fun initViews(view: View) {
        super.initViews(view)

        // Toolbar
        binding.orderToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        binding.orderNumAndDate.text = String.format(getString(R.string.order_num), args.order.id, args.order.orderTime)
        binding.orderAddress.text = args.order.address
        binding.orderDeliveryTime.text = args.order.deliveryTime + " | "
        if (args.order.status == Order.STATUS_PLACED) {
            binding.orderStatus.setTextColor(requireContext().resources.getColor(R.color.stroke_dark_orange))
            binding.orderStatus.text = getString(R.string.placed)
        }
        if (args.order.status == Order.STATUS_PREPARING) {
            binding.orderStatus.setTextColor(requireContext().resources.getColor(R.color.stroke_yellow))
            binding.orderStatus.text = getString(R.string.preparing)
        }
        if (args.order.status == Order.STATUS_ONTHEWAY) {
            binding.orderStatus.setTextColor(requireContext().resources.getColor(R.color.stroke_blue))
            binding.orderStatus.text = getString(R.string.on_the_way)
        }
        if (args.order.status == Order.STATUS_DELIVERED) {
            binding.orderStatus.setTextColor(requireContext().resources.getColor(R.color.stroke_green))
            binding.orderStatus.text = getString(R.string.delivered)

            // Order again button
            binding.orderAgainButton.visibility = View.VISIBLE
        }
        setUpDeliveryCards()
        binding.orderAgainButton.setOnClickListener {
            args.order.products.forEach {
                val productCopy = it.copy()
                productCopy.countInCart = 0
                for (i in 0 until it.countInCart) {
                    cartViewModel.addOneItem(productCopy)
                }
            }
        }

        var sum = 0
        var count = 0
        args.order.products.forEach {
            sum += it.price.toInt() * it.countInCart
            count += it.countInCart
        }
        binding.orderItemsCount.text = String.format(getString(R.string.items_count), count)
        binding.orderSubtotalValue.text = sum.toString().toPrice()
        binding.orderDeliveryfeeValue.text = if (sum >= 700) "free" else "200".toPrice()
        binding.orderTotalValue.text = if (sum >= 700) sum.toString().toPrice() else (sum + 200).toString().toPrice()

        binding.orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderRecyclerView.adapter = listAdapter
        listAdapter.submitList(args.order.products)
    }

    /*
    ?????????????????? bsd
     */
    private fun productClicked(product: Product) {
        binding.orderBottomSheet.productSheetImage.setPicture(product.imageUrl)
        binding.orderBottomSheet.also {
            it.productSheetLabel.text = product.title
            it.productSheetPrice.text = product.price.toPrice()
            it.productSheetWeight.text = product.weight
            it.productSheetCountryValue.text = product.country
            it.productSheetBrandValue.text = product.brand
            it.productSheetAmountValue.text = product.amount.toString()
            it.bottomSheetInclude.root.visibility = View.INVISIBLE
        }

        val btd = BottomSheetBehavior.from(binding.orderBottomSheet.productBottomSheetRoot)
        btd.state = BottomSheetBehavior.STATE_EXPANDED
        btd.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bgOrder.setVisibility(View.GONE)
                    binding.orderToolbar.bgToolbarCheckout.visibility = View.GONE
                    listAdapter.notifyDataSetChanged()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bgOrder.visibility = View.VISIBLE
                binding.bgOrder.alpha = slideOffset
                binding.orderToolbar.bgToolbarCheckout.visibility = View.VISIBLE
                binding.orderToolbar.bgToolbarCheckout.alpha = slideOffset
            }
        })
    }

    // ???????????????????????????? ???????????? ???????????????? ?????????? ?????????? ???? ???????????????? ???? ???????? ???????????????????? ???? ??????????????????????
    private fun setUpDeliveryCards() {
        val order = args.order
        if (order.status == Order.STATUS_PLACED) {
            fillDeliveryStatus(binding.orderPlacedStatusCard, R.color.dark_orange, R.color.stroke_dark_orange, R.drawable.ic_wallet)
        } else {
            fillDeliveryStatus(binding.orderPlacedStatusCard, R.color.white, R.color.gray1, R.drawable.ic_wallet)
        }
        // preparing
        if (order.status == Order.STATUS_PREPARING) {
            fillDeliveryStatus(binding.orderPreparingStatusCard, R.color.yellow, R.color.stroke_yellow, R.drawable.ic_shopping)
        } else if (order.status > Order.STATUS_PREPARING) {
            fillDeliveryStatus(binding.orderPreparingStatusCard, R.color.white, R.color.gray1, R.drawable.ic_shopping)
        } else {
            fillDeliveryStatus(binding.orderPreparingStatusCard, R.color.gray1, R.color.gray1, R.drawable.ic_shopping)
            /*val unwrappedDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_shopping)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, resources.getColor(R.color.text_gray))
            binding.orderPreparingStatusCard.orderStatusImage.setImageDrawable(wrappedDrawable)*/
        }
        // on the way
        if (order.status == Order.STATUS_ONTHEWAY) {
            fillDeliveryStatus(binding.orderOnthewayStatusCard, R.color.blue, R.color.stroke_blue, R.drawable.ic_sports_car)
        } else if (order.status > Order.STATUS_ONTHEWAY) {
            fillDeliveryStatus(binding.orderOnthewayStatusCard, R.color.white, R.color.gray1, R.drawable.ic_sports_car)
        } else {
            fillDeliveryStatus(binding.orderOnthewayStatusCard, R.color.gray1, R.color.gray1, R.drawable.ic_sports_car)
            /*val unwrappedDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_sports_car)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, resources.getColor(R.color.text_gray))
            binding.orderOnthewayStatusCard.orderStatusImage.setImageDrawable(wrappedDrawable)*/
        }
        // delivered
        if (order.status == Order.STATUS_DELIVERED) {
            fillDeliveryStatus(binding.orderDeliveredStatusCard, R.color.green, R.color.stroke_green, R.drawable.ic_happy)
        } else {
            fillDeliveryStatus(binding.orderDeliveredStatusCard, R.color.gray1, R.color.gray1, R.drawable.ic_happy)
            /*val unwrappedDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_happy)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, resources.getColor(R.color.text_gray))
            binding.orderDeliveredStatusCard.orderStatusImage.setImageDrawable(wrappedDrawable)*/
        }
    }

    private fun fillDeliveryStatus(card: OrderStatusCardBinding, backgroundColor: Int, strokeColor: Int, imageResourse: Int) {
        card.let {
            it.materialCardView4.setCardBackgroundColor(resources.getColor(backgroundColor))
            it.materialCardView4.strokeColor = binding.root.resources.getColor(strokeColor)
            it.orderStatusImage.setImageDrawable(resources.getDrawable(imageResourse))
        }
    }
}
