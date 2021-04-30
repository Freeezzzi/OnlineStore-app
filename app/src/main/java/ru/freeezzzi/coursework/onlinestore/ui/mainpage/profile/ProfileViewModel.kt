package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logOut() {
        authRepository.logOut()
    }
}
