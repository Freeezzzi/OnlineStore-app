package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val mutableCategoriesList = MutableLiveData<ViewState<List<Category>, String?>>()

    val categoriesList: LiveData<ViewState<List<Category>, String?>>
        get() = mutableCategoriesList

    private var fullList: List<Category> = mutableListOf()

    fun searchAction(query: String) {
        val list = mutableListOf<Category>()
        fullList.forEach {
            if (it.title.contains(query, true)) list.add(it)
        }
        mutableCategoriesList.value = ViewState.success(list)
    }

    fun clearSearchAction() {
        mutableCategoriesList.value = ViewState.success(fullList)
    }

    fun getCategories() {
        viewModelScope.launch {
            mutableCategoriesList.value = ViewState.loading()
            when (val result = categoriesRepository.getCategories()) {
                is OperationResult.Success -> {
                    fullList = result.data
                    mutableCategoriesList.value = ViewState.success(result.data)
                }
                is OperationResult.Error -> {
                    mutableCategoriesList.value = ViewState.error(fullList, result.data)
                }
            }
        }
    }
}

/*
val list: List<Category> = mutableListOf(
                Category(
                    0, "fruits", "https://miro.medium.com/max/468/1*Aq99R6jM608RgF_663kmAA.png"
                ),
                Category(
                    1, "vegetables", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    2, "meat", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                ),
                Category(
                    3, "cupcakes", "https://miro.medium.com/max/700/1*qtCaJ3SscNBGVuKEHfl7Rw.gif"
                )
            )
            fullList = list
            mutableCategoriesList.value = ViewState.success(list)
 */
