package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout.editaddress

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.EditAddressBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout.CheckoutViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditAddressFragment : BaseFragment(R.layout.edit_address) {
    private val binding: EditAddressBinding by viewBinding(EditAddressBinding::bind)

    private val viewModel: CheckoutViewModel by activityViewModels()

    override fun initViews(view: View) {
        super.initViews(view)

        //Toolbar
        binding.editAddressToolbar.toolbarTitle.setText(getString(R.string.edit_address))
        binding.editAddressToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        //основной UI
        binding.editAddressName.setText(viewModel.user.name)
        val slots = PredefinedSlots.RUS_PHONE_NUMBER
        val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.editAddressPhone)
        binding.editAddressPhone.setText(viewModel.user.phone)

        if (viewModel.user.address != null) {
            binding.editAddressStreet.setText(viewModel.user.address!!.streetAndHouse)
            binding.editAddressFlatnum.setText(viewModel.user.address!!.apart)
            binding.editAddressFloor.setText(viewModel.user.address!!.floor)
            binding.editAddressEntranceNum.setText(viewModel.user.address!!.floor)
        }
        binding.editAddressSaveButton.setOnClickListener {
            if (binding.editAddressName.text.isNotBlank() &&
                binding.editAddressPhone.text.length == 18 &&
                binding.editAddressStreet.text.isNotBlank()
            ) {
                viewModel.saveAddress(
                        name = binding.editAddressName.text.toString(),
                        phone = binding.editAddressPhone.text.toString(),
                        streetAndHouse = binding.editAddressStreet.text.toString(),
                        apart = binding.editAddressFlatnum.text.toString(),
                        floor = binding.editAddressFloor.text.toString(),
                        entrance = binding.editAddressEntranceNum.text.toString()
                )
                Navigation.findNavController(binding.root).navigateUp()
            } else {
                // TODO вывести ошибку
            }
        }
    }
}
