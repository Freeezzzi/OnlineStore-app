package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.telephony.PhoneNumberUtils
import android.text.InputType
import android.util.Patterns
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.LoginFragmentBinding

class LoginViewHolder(
    private val binding: LoginFragmentBinding
) : ViewPagerViewHolder(binding.root) {
    fun onBind(loginCallback: (String, String) -> Unit) {
        setUpHints()

        binding.logInButton.setOnClickListener {
            if (binding.emailOrPhoneCard.fieldCardText.text.isNotBlank() &&
                binding.passwordCard.fieldCardText.text.isNotBlank()
            ) {
                val text = getLogin()
                if (text != null) {
                    loginCallback.invoke(text, binding.passwordCard.fieldCardText.text.toString())
                } else {
                    // TODO вывести ошибку
                }
            }
        }
    }

    private fun getLogin(): String? {
        val text = binding.emailOrPhoneCard.fieldCardText.text.toString()
        if (text.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(text).matches()) { // email
            return text.trim()
        }
        if (text.isNotBlank() && Patterns.PHONE.matcher(text).matches()) {
            return PhoneNumberUtils.formatNumber(text, "RU")
        }
        return null
    }

    private fun setUpHints() {
        binding.emailOrPhoneCard.fieldCardText.hint = binding.root.context.getString(R.string.email_or_phone)
        binding.passwordCard.fieldCardText.hint = binding.root.context.getString(R.string.password)
        binding.passwordCard.fieldCardText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
}
