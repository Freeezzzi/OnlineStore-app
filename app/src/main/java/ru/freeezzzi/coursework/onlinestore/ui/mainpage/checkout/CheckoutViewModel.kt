package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    var productsList: List<Product> = mutableListOf()

    fun countItems(): Int{
        var sum = 0
        productsList.forEach { sum += it.countInCart }
        return sum
    }
}
