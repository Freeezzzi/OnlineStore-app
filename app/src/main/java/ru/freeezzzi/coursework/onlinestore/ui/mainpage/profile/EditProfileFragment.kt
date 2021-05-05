package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.Patterns
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.EditProfileBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditProfileFragment() : BaseFragment(R.layout.edit_profile) {
    private val binding: EditProfileBinding by viewBinding(EditProfileBinding::bind)

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun initViews(view: View) {
        super.initViews(view)

        viewModel.clearChangeResult()
        viewModel.changeResult.observe(viewLifecycleOwner, ::changeResult)

        // Toolbar
        binding.editProfileToolbar.toolbarTitle.setText(getString(R.string.edit_profile))
        binding.editProfileToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        // name
        binding.editProfileName.setText(viewModel.user.value!!.name)

        // phone
        val slots = PredefinedSlots.RUS_PHONE_NUMBER
        val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.editProfilePhone)
        binding.editProfilePhone.setText(viewModel.user.value!!.phone)

        // email
        binding.editProfileEmail.setText(viewModel.user.value!!.email)

        // pwd
        binding.pwdVisibility.visibility = View.VISIBLE
        binding.pwdVisibility.setOnClickListener {
            binding.editProfilePassword.transformationMethod = SingleLineTransformationMethod()
            it.visibility = View.INVISIBLE
            binding.pwdVisibilityOff.visibility = View.VISIBLE
            binding.editProfilePassword.setSelection(binding.editProfilePassword.text.toString().length)
        }
        binding.pwdVisibilityOff.setOnClickListener {
            binding.editProfilePassword.transformationMethod = PasswordTransformationMethod()
            it.visibility = View.INVISIBLE
            binding.pwdVisibility.visibility = View.VISIBLE
            binding.editProfilePassword.setSelection(binding.editProfilePassword.text.toString().length)
        }

        binding.editProfileApply.setOnClickListener {
            if (
                binding.editProfileName.text.isNotBlank() &&
                binding.editProfilePhone.text.length == 18 &&
                binding.editProfileEmail.text?.length ?: 0 > 0 &&
                isEmail(binding.editProfileEmail.text.toString().trim())
            ) {
                viewModel.setUserProfile(
                    name = binding.editProfileName.text.toString().trim(),
                    phone = binding.editProfilePhone.text.toString().trim(),
                    email = binding.editProfileEmail.text.toString().trim(),
                    pwd = if (binding.editProfilePassword.text.isNotBlank()) binding.editProfilePassword.text.toString().trim() else viewModel.user.value!!.pwd
                )
            } else {
                // TODo вывести ошибку
            }
        }
    }

    fun changeResult(state: ViewState<Boolean, String?>) {
        when (state) {
            is ViewState.Success -> {
                binding.editProfileProgressBar.visibility = View.INVISIBLE
                Navigation.findNavController(binding.root).navigateUp()
            }
            is ViewState.Loading -> {
                binding.editProfileProgressBar.visibility = View.VISIBLE
            }
            is ViewState.Error -> {
                binding.editProfileProgressBar.visibility = View.INVISIBLE
                // TODO show error
            }
        }
    }

    private fun isEmail(text: String) = !text.isBlank() && Patterns.EMAIL_ADDRESS.matcher(text).matches()
}
