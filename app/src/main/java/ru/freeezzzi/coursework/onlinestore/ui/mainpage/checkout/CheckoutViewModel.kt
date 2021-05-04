package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Address
import ru.freeezzzi.coursework.onlinestore.domain.models.Order
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.models.User
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.OrdersRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CheckoutViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val ordersRepository: OrdersRepository
) : ViewModel() {
    var productsList: List<Product> = mutableListOf()

    val orderStatus: MutableLiveData<ViewState<Boolean, String?>> = MutableLiveData()

    val user: User = authRepository.loadUser()!!

    var deliveryTime: String = ""

    var deliveryDate: String = ""

    fun saveAddress(name: String, phone: String, streetAndHouse: String, apart: String, entrance: String, floor: String) {
        user.address = Address(
            name = name,
            phone = phone,
            streetAndHouse = streetAndHouse,
            apart = apart,
            entrance = entrance,
            floor = floor
        )
        authRepository.saveUser(user)
    }

    fun placeOrder() {
        viewModelScope.launch {
            orderStatus.value = ViewState.loading()
            val orderTime = SimpleDateFormat("dd MMM yy hh:mm").format(Calendar.getInstance().time)
            val order = Order(
                id = 0,
                user_id = user.id,
                status = Order.STATUS_PLACED,
                products = productsList as ArrayList<Product>,
                address = user.address.toString(),
                orderTime = orderTime,
                deliveryTime = deliveryDate + " " + deliveryTime
            )
            when (val status = ordersRepository.placeOrder(order)) {
                is OperationResult.Success -> {
                    var sum = 0
                    productsList.forEach {
                        sum += it.countInCart * it.price.toInt()
                    }
                    user.balance -= sum
                    authRepository.saveUser(user)
                    orderStatus.value = ViewState.success(true)
                }
                is OperationResult.Error -> orderStatus.value = ViewState.error(false, status.data)
            }
        }
    }
}
