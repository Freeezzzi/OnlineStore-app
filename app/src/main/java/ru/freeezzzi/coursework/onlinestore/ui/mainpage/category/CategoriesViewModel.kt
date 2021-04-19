package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val categoriesRepository : CategoriesRepository
) : ViewModel() {

    fun getCategories(){

    }
}