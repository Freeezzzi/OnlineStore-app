package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun register(name: String, email: String, phone: String, pwd: String) {
    }

    fun login(login: String, pwd: String) {
    }
}
