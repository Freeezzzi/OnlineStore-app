package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.profile_fragment.view.*
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.OrderStatusCardBinding
import ru.freeezzzi.coursework.onlinestore.databinding.ProfileFragmentBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.SplashActivity

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {
    private val binding by viewBinding(ProfileFragmentBinding::bind)

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun initViews(view: View) {
        super.initViews(view)

        setOnClickListeners()
        fillUi()
        setUpToolbar()

        //set up delivery cards
        fillDeliveryStatus(binding.placedStatusCard, getString(R.string.placed),R.color.dark_orange, R.color.stroke_dark_orange)
        fillDeliveryStatus(binding.preparingStatusCard, getString(R.string.preparing),R.color.yellow, R.color.stroke_yellow)
        fillDeliveryStatus(binding.onthewayStatusCard, getString(R.string.on_the_way),R.color.blue, R.color.stroke_blue)
        fillDeliveryStatus(binding.deliveredStatusCard, getString(R.string.delivered),R.color.green, R.color.stroke_green)
    }

    private fun setOnClickListeners() {
        binding.signOutCard.setOnClickListener {
            viewModel.logOut()
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.editAddressClick.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditAddressFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.profileCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.textView21.setOnClickListener {
            val orderType = Order.ORDERS_ALL
            val action = ProfileFragmentDirections.actionProfileFragmentToOrdersListFragment(orderType)
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.imageView9.setOnClickListener {
            val orderType = Order.ORDERS_ALL
            val action = ProfileFragmentDirections.actionProfileFragmentToOrdersListFragment(orderType)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    fun setUpToolbar(){
        binding.profileToolbar.toolbarBackButton.visibility = View.INVISIBLE
        binding.profileToolbar.toolbarTitle.text = getString(R.string.profile)
    }

    private fun fillDeliveryStatus(card: OrderStatusCardBinding, orderStatus: String, backgroundColor: Int, strokeColor:Int) {
        card.let {
            it.orderStatusText.typeface = Typeface.DEFAULT_BOLD
            it.orderStatusText.text = orderStatus
            it.materialCardView4.setCardBackgroundColor(resources.getColor(backgroundColor))
            it.materialCardView4.strokeColor = binding.root.resources.getColor(strokeColor)
            it.root.setOnClickListener {
                var orderType = Order.ORDERS_ALL
                if (orderStatus == getString(R.string.placed)) orderType = Order.STATUS_PLACED
                if (orderStatus == getString(R.string.preparing)) orderType = Order.STATUS_PREPARING
                if (orderStatus == getString(R.string.on_the_way)) orderType = Order.STATUS_ONTHEWAY
                if (orderStatus == getString(R.string.delivered)) orderType = Order.STATUS_DELIVERED
                val action = ProfileFragmentDirections.actionProfileFragmentToOrdersListFragment(orderType)
                Navigation.findNavController(binding.root).navigate(action)
            }
            //TODO setPicture
        }
    }

    private fun fillUi() {
        binding.profileCard.apply {
            profile_name.text = viewModel.user.value!!.name
            profile_phone.text = viewModel.user.value!!.phone
            // TODO set picture
        }
    }
}
