package ru.freeezzzi.coursework.onlinestore.ui.mainpage.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.data.local.LocalDatabase
import ru.freeezzzi.coursework.onlinestore.data.local.entities.CartProductId
import ru.freeezzzi.coursework.onlinestore.data.local.entities.RecentlyWatchedId
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val productsRepository: ProductsRepository,
    private val database: LocalDatabase
) : ViewModel() {
    // TODO recently watched
    private val mutableRecentlyWatched = MutableLiveData<ViewState<List<Product>, String?>>()
    val recentlyWatched: LiveData<ViewState<List<Product>, String?>>
        get() = mutableOnSaleProductsList

    // On sale
    private val mutableOnSaleProductsList = MutableLiveData<ViewState<List<Product>, String?>>()
    val onSaleproductsList: LiveData<ViewState<List<Product>, String?>>
        get() = mutableOnSaleProductsList

    // For sales fragment
    private val mutableProductsList = MutableLiveData<ViewState<List<Product>, String?>>()
    val productsList: LiveData<ViewState<List<Product>, String?>>
        get() = mutableProductsList
    private var fullList: List<Product> = mutableListOf()

    // for categories grid recycler view 
    private val mutableCategoriesList = MutableLiveData<ViewState<List<Category>, String?>>()
    val categoriesList: LiveData<ViewState<List<Category>, String?>>
        get() = mutableCategoriesList

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

    fun getProductsOnSale() {
        viewModelScope.launch {
            mutableOnSaleProductsList.value = ViewState.loading()
            when (val result = productsRepository.getProductsOnSale()) {
                is OperationResult.Success -> {
                    mutableOnSaleProductsList.value = ViewState.success(result.data)
                }
                is OperationResult.Error -> {
                    mutableOnSaleProductsList.value = ViewState.error(mutableListOf(), result.data)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            mutableCategoriesList.value = ViewState.loading()
            when (val result = categoriesRepository.getCategories()) {
                is OperationResult.Success -> {
                    mutableCategoriesList.value = ViewState.success(result.data)
                }
                is OperationResult.Error -> {
                    mutableCategoriesList.value = ViewState.error(mutableListOf(), result.data)
                }
            }
        }
    }

    fun saveToRecentlyWathced(product: Product) {
        viewModelScope.launch {
            database.productsDao().insert(RecentlyWatchedId(Calendar.getInstance().timeInMillis, product.id))
        }
    }

    fun getRecentlyWathced() {
        viewModelScope.launch {
            mutableRecentlyWatched.value = ViewState.loading()
            val ids = mutableListOf<Long>()
            database.productsDao().getRecentlyWatched().forEach { ids.add(it.id ?: 0) }
            when (val result = productsRepository.getProductByIds(ids)) {
                is OperationResult.Success -> mutableRecentlyWatched.value = ViewState.success(result.data)
                is OperationResult.Error -> mutableRecentlyWatched.value = ViewState.error(
                    mutableListOf(), result.data
                )
            }
        }
    }
}
