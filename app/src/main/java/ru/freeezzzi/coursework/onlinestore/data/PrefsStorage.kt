package ru.freeezzzi.coursework.onlinestore.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.domain.models.Address
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
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
        val id = sharedPref.getLong(ID_KEY, -1)
        val pwd = sharedPref.getString(PASSWORD_KEY, null)
        val name = sharedPref.getString(NAME_KEY, null)
        val token = sharedPref.getString(TOKEN_KEY, null)
        val email = sharedPref.getString(EMAIL_KEY, null)
        val phone = sharedPref.getString(PHONE_KEY, null)
        val balance = sharedPref.getLong(BALANCE_KEY, -1)
        val addressName = sharedPref.getString(ADDRESS_NAME_KEY, "")
        val addressPhone = sharedPref.getString(ADDRESS_PHONE_KEY, "")
        val streetAndHouse = sharedPref.getString(STREETANDHOUSE_KEY, "")
        val apart = sharedPref.getString(APARTAMENTS_KEY, "")
        val entrance = sharedPref.getString(ENTRANCE_KEY, "")
        val floor = sharedPref.getString(FLOOR_KEY, "")

        if (id != -1L && !pwd.isNullOrBlank() && !name.isNullOrBlank() && !token.isNullOrBlank() &&
            !email.isNullOrBlank() && !phone.isNullOrBlank() && balance != -1L
        ) {
            var address: Address? = null
            if (!addressName.isNullOrBlank() && !addressPhone.isNullOrBlank() && !streetAndHouse.isNullOrBlank()) {
                address = Address(
                    name = addressName,
                    phone = addressPhone,
                    streetAndHouse = streetAndHouse,
                    apart = apart ?: "",
                    entrance = entrance ?: "",
                    floor = floor ?: ""

                )
            }
            return User(
                id = id,
                pwd = pwd,
                name = name,
                token = token,
                email = email,
                phone = phone,
                balance = balance,
                address = address
            )
        }

        return null
    }

    fun saveToSharedPref(
        user: User?
    ) {
        if (user != null) {
            sharedPref.edit()
                .putLong(ID_KEY, user.id)
                .putString(PASSWORD_KEY, user.pwd)
                .putString(NAME_KEY, user.name)
                .putString(TOKEN_KEY, user.token)
                .putString(PHONE_KEY, user.phone)
                .putString(EMAIL_KEY, user.email)
                .putLong(BALANCE_KEY, user.balance)
                .apply()
            if (user.address != null) {
                sharedPref.edit()
                    .putString(ADDRESS_NAME_KEY, user.address!!.name)
                    .putString(ADDRESS_PHONE_KEY, user.address!!.phone)
                    .putString(STREETANDHOUSE_KEY, user.address!!.streetAndHouse)
                    .putString(APARTAMENTS_KEY, user.address!!.apart)
                    .putString(ENTRANCE_KEY, user.address!!.entrance)
                    .putString(FLOOR_KEY, user.address!!.floor)
                    .apply()
            }
        } else {
            sharedPref.edit()
                .remove(ID_KEY)
                .remove(PASSWORD_KEY)
                .remove(NAME_KEY)
                .remove(TOKEN_KEY)
                .remove(PHONE_KEY)
                .remove(BALANCE_KEY)
                .remove(EMAIL_KEY)
                .remove(ADDRESS_NAME_KEY)
                .remove(ADDRESS_PHONE_KEY)
                .remove(STREETANDHOUSE_KEY)
                .remove(APARTAMENTS_KEY)
                .remove(ENTRANCE_KEY)
                .remove(FLOOR_KEY)
                .apply()
        }
        userLiveData.postValue(user)
    }

    companion object {
        private const val ID_KEY = "id"
        private const val PASSWORD_KEY = "username"
        private const val NAME_KEY = "name"
        private const val PHONE_KEY = "phone"
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
        private const val BALANCE_KEY = "balance"
        private const val ADDRESS_NAME_KEY = "address_name"
        private const val ADDRESS_PHONE_KEY = "address_phone"
        private const val STREETANDHOUSE_KEY = "street_and_house"
        private const val APARTAMENTS_KEY = "apartaments"
        private const val ENTRANCE_KEY = "entrance"
        private const val FLOOR_KEY = "floor"
    }
}
