package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val mutableLoginResult: MutableLiveData<ViewState<Unit, String?>> = MutableLiveData()

    val loginResult: LiveData<ViewState<Unit, String?>> get() = mutableLoginResult

    private val mutableRegistrationResult: MutableLiveData<ViewState<Unit, String?>> =
        MutableLiveData()

    val registrationResult: LiveData<ViewState<Unit, String?>> get() = mutableRegistrationResult

    fun register(name: String, email: String, phone: String, pwd: String) {
        viewModelScope.launch {
            mutableRegistrationResult.value = ViewState.loading()

            val result = authRepository.register(
                name,
                pwd,
                phone,
                email
            )
            mutableRegistrationResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(Unit, result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun login(login: String, pwd: String) {
        viewModelScope.launch {
            mutableLoginResult.value = ViewState.loading()

            val result = authRepository.login(login, pwd)
            mutableLoginResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(Unit, result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}
