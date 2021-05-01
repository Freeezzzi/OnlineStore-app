package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val mutableCartList = MutableLiveData<List<Product>>()

    val cartList: LiveData<List<Product>>
        get() = mutableCartList

    fun initializeCart() {
        mutableCartList.value = listOf()
    }

    /**
     * Добавляет продукт в корзину если его нет или увеличивает их кол-во на 1 если уже есть
     */
    fun addOneItem(product: Product) {
        val cart = mutableCartList.value!!.toMutableList()
        val it = cart.find {
            it.equals(product)
        }
        if (it != null) {
            it.countInCart++
            product.countInCart = it.countInCart
            mutableCartList.value = cart
        } else {
            cart.add(product)
            product.countInCart++
            mutableCartList.value = cart
        }
    }

    /**
     * Удаляет 1 продукт из корзины
     */
    fun removeOneItem(product: Product) {
        val cart = mutableCartList.value!!.toMutableList()
        val it = cart.find {
            it.equals(product)
        }
        if (it == null) { // такого нет
            product.countInCart = 0
        } else { // если такой есть
            if (it.countInCart == 1) {
                cart.remove(it)
                mutableCartList.value = cart
                product.countInCart = 0
            } else {
                it.countInCart--
                product.countInCart = it.countInCart
                mutableCartList.value = cart
            }
        }
    }

    fun isInCart(product: Product) {
        val cart = mutableCartList.value!!
        val it = cart.find {
            it.equals(product)
        }
        if (it == null) { // такого нет
            product.countInCart = 0
        } else { // если такой есть
            product.countInCart =  it.countInCart
        }
    }
}
