package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.EditAddressBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class ProfileEditAddressFragment() : BaseFragment(R.layout.edit_address) {
    private val binding: EditAddressBinding by viewBinding(EditAddressBinding::bind)

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun initViews(view: View) {
        super.initViews(view)

        // Toolbar
        binding.editAddressToolbar.toolbarTitle.setText(getString(R.string.edit_address))
        binding.editAddressToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        // основной UI
        binding.editAddressName.setText(viewModel.user.value!!.name)
        val slots = PredefinedSlots.RUS_PHONE_NUMBER
        val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.editAddressPhone)
        binding.editAddressPhone.setText(viewModel.user.value!!.phone)

        if (viewModel.user.value!!.address != null) {
            binding.editAddressStreet.setText(viewModel.user.value!!.address!!.streetAndHouse)
            binding.editAddressFlatnum.setText(viewModel.user.value!!.address!!.apart)
            binding.editAddressFloor.setText(viewModel.user.value!!.address!!.floor)
            binding.editAddressEntranceNum.setText(viewModel.user.value!!.address!!.floor)
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
