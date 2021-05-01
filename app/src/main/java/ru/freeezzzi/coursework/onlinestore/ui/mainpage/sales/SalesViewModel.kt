package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val mutableProductsList = MutableLiveData<ViewState<List<Product>, String?>>()

    val productsList: LiveData<ViewState<List<Product>, String?>>
        get() = mutableProductsList

    private var fullList: List<Product> = mutableListOf()

    fun getProductsByCategory(categoryId: Long) {
        viewModelScope.launch {
            mutableProductsList.value = ViewState.loading()
            when (val result = productsRepository.getProductsByCategory(categoryId)) {
                is OperationResult.Success -> {
                    fullList = result.data
                    mutableProductsList.value = ViewState.success(result.data)
                }
                is OperationResult.Error -> {
                    mutableProductsList.value = ViewState.error(fullList, result.data)
                }
            }
        }
    }
}
