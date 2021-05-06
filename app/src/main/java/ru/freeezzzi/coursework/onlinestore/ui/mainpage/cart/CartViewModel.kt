package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.data.local.LocalDatabase
import ru.freeezzzi.coursework.onlinestore.data.local.entities.CartProductId
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val database: LocalDatabase
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
        // ссылку нужно изменять
        val cart = mutableListOf<Product>()
        mutableCartList.value!!.forEach {
            cart.add(it)
        }
        if (product.countInCart + 1 > product.amount) return // если на складе больше нет

        val it = cart.find {
            it.equals(product)
        }
        if (it != null) {
            if (it.countInCart + 1 > product.amount) return
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
        // ссылку нужно изменять
        val cart = mutableListOf<Product>()
        mutableCartList.value!!.forEach {
            cart.add(it)
        }
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
            product.countInCart = it.countInCart
        }
    }

    fun saveCart() {
        viewModelScope.launch {
            cartList.value!!.forEach {
                database.productsDao().insert(CartProductId(it.id, it.countInCart))
            }
        }
    }

    fun loadCart() {
        viewModelScope.launch {
            val productIds = mutableListOf<Long>()
            val countList = mutableListOf<Int>()
            database.productsDao().getCart().forEach {
                productIds.add(it.id ?: 0)
                countList.add(it.countInCart)
                database.productsDao().delete(it)
            }
            when (val result = productsRepository.getProductByIds(productIds)) {
                is OperationResult.Success -> {
                    result.data.forEachIndexed { index, product ->
                        product.countInCart = countList[index]
                    }
                    mutableCartList.value = result.data!!
                }
                is OperationResult.Error -> {
                    mutableCartList.value = mutableListOf()
                }
            }
        }
    }
}
