package ru.freeezzzi.coursework.onlinestore.ui.mainpage.sales

import androidx.lifecycle.ViewModel
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {


}