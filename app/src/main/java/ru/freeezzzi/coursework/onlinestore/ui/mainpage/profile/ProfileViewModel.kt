package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Address
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.domain.models.User
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.OrdersRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val ordersRepository: OrdersRepository
) : ViewModel() {
    private val mutableUser = MutableLiveData<User>(authRepository.loadUser())
    val user: LiveData<User> get() = mutableUser

    private val mutableChangeResult = MutableLiveData<ViewState<Boolean, String?>>()
    val changeResult: LiveData<ViewState<Boolean, String?>> get() = mutableChangeResult

    private val mutableOrdersList = MutableLiveData<ViewState<List<Order>, String?>>()
    val ordersList: LiveData<ViewState<List<Order>, String?>> get() = mutableOrdersList
    private var fullOrdersList: List<Order> = mutableListOf<Order>()

    fun getOrders(type: Int) { // Order companion object
        viewModelScope.launch {
            mutableOrdersList.value = ViewState.loading()
            when (val result = ordersRepository.getOrders(user.value!!.id)) {
                is OperationResult.Success -> {
                    fullOrdersList = result.data
                    val requiredList = mutableListOf<Order>()
                    fullOrdersList.forEach {
                        if (it.status == type) requiredList.add(it)
                    }
                    mutableOrdersList.value = ViewState.success(requiredList)
                }
                is OperationResult.Error -> {
                    val requiredList = mutableListOf<Order>()
                    fullOrdersList.forEach {
                        if (it.status == type) requiredList.add(it)
                    }
                    mutableOrdersList.value = ViewState.error(requiredList, result.data)
                }
            }
        }
    }

    fun logOut() {
        authRepository.logOut()
    }

    fun saveAddress(name: String, phone: String, streetAndHouse: String, apart: String, entrance: String, floor: String) {
        user.value!!.address = Address(
            name = name,
            phone = phone,
            streetAndHouse = streetAndHouse,
            apart = apart,
            entrance = entrance,
            floor = floor
        )
        authRepository.saveUser(user.value!!)
    }

    fun setUserProfile(
        name: String,
        phone: String,
        email: String,
        pwd: String
    ) {
        viewModelScope.launch {
            val newUser = User(
                token = user.value!!.token,
                id = user.value!!.id,
                pwd = pwd,
                name = name,
                phone = phone,
                email = email,
                balance = user.value!!.balance
            )
            mutableChangeResult.value = ViewState.loading()
            when (val result = authRepository.updateUserOnServer(newUser)) {
                is OperationResult.Success -> {
                    if (result.data) {
                        authRepository.saveUser(newUser)
                        mutableUser.value = authRepository.loadUser()
                    }
                    mutableChangeResult.value = ViewState.success(result.data)
                }
                is OperationResult.Error -> mutableChangeResult.value = ViewState.error(false, result.data)
            }
        }
    }
}
