package ru.freeezzzi.coursework.onlinestore.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.domain.models.User
import javax.inject.Inject

class PrefsStorage @Inject constructor(
    context: Context
) {
    private val sharedPref = context.getSharedPreferences(
        context.resources.getString(R.string.shared_pref_name),
        Context.MODE_PRIVATE
    )

    private val userLiveData = MutableLiveData(loadUser())

    fun observeUser(): LiveData<User?> = userLiveData

    fun loadUser(): User? {
        val pwd = sharedPref.getString(PASSWORD_KEY, null)
        val name = sharedPref.getString(NAME_KEY, null)
        val token = sharedPref.getString(TOKEN_KEY, null)
        val email = sharedPref.getString(EMAIL_KEY, null)
        val phone = sharedPref.getString(PHONE_KEY, null)
        val balance = sharedPref.getLong(BALANCE_KEY, -1)

        if (!pwd.isNullOrBlank() && !name.isNullOrBlank() && !token.isNullOrBlank() &&
            !email.isNullOrBlank() && !phone.isNullOrBlank() && balance != -1L
        )
            return User(
                pwd = pwd,
                name = name,
                token = token,
                email = email,
                phone = phone,
                balance = balance
            )

        return null
    }

    fun saveToSharedPref(
        user: User?
    ) {
        if (user != null) {
            sharedPref.edit()
                .putString(PASSWORD_KEY, user.pwd)
                .putString(NAME_KEY, user.name)
                .putString(TOKEN_KEY, user.token)
                .putString(PHONE_KEY, user.phone)
                .putString(EMAIL_KEY, user.email)
                .putLong(BALANCE_KEY, user.balance)
                .apply()
        } else {
            sharedPref.edit()
                .remove(PASSWORD_KEY)
                .remove(NAME_KEY)
                .remove(TOKEN_KEY)
                .remove(PHONE_KEY)
                .remove(BALANCE_KEY)
                .remove(EMAIL_KEY)
                .apply()
        }
        userLiveData.postValue(user)
    }

    companion object {
        private const val PASSWORD_KEY = "username"
        private const val NAME_KEY = "name"
        private const val PHONE_KEY = "phone"
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
        private const val BALANCE_KEY = "balance"
    }
}
