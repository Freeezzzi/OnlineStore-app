package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.models.Address
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.models.User
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    var productsList: List<Product> = mutableListOf()

    val user: User = authRepository.loadUser()!!

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
}
