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
        val username = sharedPref.getString(USERNAME_KEY, null)
        val name = sharedPref.getString(NAME_KEY, null)
        val token = sharedPref.getString(TOKEN_KEY, null)

        if (!username.isNullOrBlank() && !name.isNullOrBlank() && !token.isNullOrBlank())
            return User(
                username = username,
                name = name,
                token = token,
            )

        return null
    }

    fun saveToSharedPref(
        user: User?
    ) {
        if (user != null) {
            sharedPref.edit()
                .putString(USERNAME_KEY, user.username)
                .putString(NAME_KEY, user.name)
                .putString(TOKEN_KEY, user.token)
                .apply()
        } else {
            sharedPref.edit()
                .remove(USERNAME_KEY)
                .remove(NAME_KEY)
                .remove(TOKEN_KEY)
                .apply()
        }
        userLiveData.postValue(user)
    }

    companion object {
        private const val USERNAME_KEY = "username"
        private const val NAME_KEY = "name"
        private const val TOKEN_KEY = "token"
    }
}