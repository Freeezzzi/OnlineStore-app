package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.Patterns
import android.view.View
import androidx.core.view.isVisible
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.LoginFieldCardBinding
import ru.freeezzzi.coursework.onlinestore.databinding.SignUpFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class RegisterViewHolder(
    private val binding: SignUpFragmentBinding
) : ViewPagerViewHolder(binding.root) {

    fun onBind(
        // name, email,phone,number
        registerCallback: (String, String, String, String) -> Unit
    ) {

        setUpHints()
        setUpTextChangeListener(binding.signUpNameCard)
        setUpEmailValidation()
        setUpPhoneValidation()
        setUpPasswordField()
        // setUpTextChangeListener(binding.signUpPasswordCard)
        binding.signUpButton.setOnClickListener {
            if (validateFields()) {
                registerCallback.invoke(
                    binding.signUpNameCard.fieldCardText.text.toString().trim(),
                    binding.signUpEmailCard.fieldCardText.text.toString().trim(),
                    binding.signUpPhoneCard.fieldCardText.text.toString().trim(),
                    binding.signUpPasswordCard.fieldCardText.text.toString().trim()
                )
            }
        }
    }

    private fun validateFields() =
        binding.signUpNameCard.fieldCardChecked.isVisible &&
            binding.signUpEmailCard.fieldCardChecked.isVisible &&
            binding.signUpPhoneCard.fieldCardChecked.isVisible &&
            binding.signUpPasswordCard.fieldCardText.text.isNotBlank()

    /**
     * UI SETUP
     */
    private fun setUpHints() {
        binding.signUpNameCard.fieldCardText.hint = binding.root.context.getString(R.string.name)
        binding.signUpEmailCard.fieldCardText.hint = binding.root.context.getString(R.string.email)
        binding.signUpPhoneCard.fieldCardText.hint = binding.root.context.getString(R.string.phone_number)
        binding.signUpPhoneCard.fieldCardText.inputType = InputType.TYPE_CLASS_PHONE
        binding.signUpPasswordCard.fieldCardText.hint = binding.root.context.getString(R.string.password)
        binding.signUpPasswordCard.fieldCardText.transformationMethod = PasswordTransformationMethod()
        binding.signUpPasswordCard.fieldCardNextButton.setOnClickListener { binding.signUpPasswordCard.fieldCardText.clearFocus() }
    }

    private fun setUpPhoneValidation() {
        val slots = PredefinedSlots.RUS_PHONE_NUMBER
        val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.signUpPhoneCard.fieldCardText)

        // ?????????????????? ????????????????
        binding.signUpPhoneCard.fieldCardText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length ?: 0 == 18) { // ???????? ????????????????
                    binding.signUpPhoneCard.fieldCardChecked.visibility = View.VISIBLE
                } else {
                    binding.signUpPhoneCard.fieldCardChecked.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun setUpEmailValidation() {
        // ?????????????????? ????????????????
        binding.signUpEmailCard.fieldCardText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length ?: 0 > 0 && isEmail(p0.toString().trim())) { // ???????? ????????????????
                    binding.signUpEmailCard.fieldCardChecked.visibility = View.VISIBLE
                } else {
                    binding.signUpEmailCard.fieldCardChecked.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun setUpTextChangeListener(cardBinding: LoginFieldCardBinding) {
        cardBinding.fieldCardText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length ?: 0 > 0 && !p0!!.toString().isBlank()) {
                    cardBinding.fieldCardNextButton.visibility = View.VISIBLE
                }
                if (p0?.length ?: 0 == 0) {
                    cardBinding.fieldCardNextButton.visibility = View.GONE
                }
            }
        })
        cardBinding.fieldCardText.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    cardBinding.fieldCardNextButton.visibility = View.VISIBLE
                    cardBinding.fieldCardChecked.visibility = View.INVISIBLE
                } else {
                    cardBinding.fieldCardNextButton.visibility = View.GONE
                    cardBinding.fieldCardChecked.visibility = View.VISIBLE
                }
            }
        })

        cardBinding.fieldCardNextButton.setOnClickListener {
            it.clearFocus()
            val next = it.focusSearch(View.FOCUS_DOWN)
            if (next != null) next.requestFocus()
        }
    }

    private fun setUpPasswordField() {
/*        binding.signUpPasswordCard.fieldCardText.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signUpPasswordCard.fieldCardText.text.isNotBlank()) {
                    binding.signUpPasswordCard.fieldCardChecked.visibility = View.VISIBLE
                }
            }
        })*/
        binding.signUpPasswordCard.fieldVisibility.visibility = View.VISIBLE
        binding.signUpPasswordCard.fieldVisibility.setOnClickListener {
            binding.signUpPasswordCard.fieldCardText.transformationMethod = SingleLineTransformationMethod()
            it.visibility = View.INVISIBLE
            binding.signUpPasswordCard.fieldVisibilityOff.visibility = View.VISIBLE
            binding.signUpPasswordCard.fieldCardText.setSelection(binding.signUpPasswordCard.fieldCardText.text.toString().length)
        }
        binding.signUpPasswordCard.fieldVisibilityOff.setOnClickListener {
            binding.signUpPasswordCard.fieldCardText.transformationMethod = PasswordTransformationMethod()
            it.visibility = View.INVISIBLE
            binding.signUpPasswordCard.fieldVisibility.visibility = View.VISIBLE
            binding.signUpPasswordCard.fieldCardText.setSelection(binding.signUpPasswordCard.fieldCardText.text.toString().length)
        }
    }

    private fun isEmail(text: String) = !text.isBlank() && Patterns.EMAIL_ADDRESS.matcher(text).matches()
    /**
     * UI SETUP END
     */
}
