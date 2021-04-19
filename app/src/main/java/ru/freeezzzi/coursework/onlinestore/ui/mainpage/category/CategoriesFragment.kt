package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CategoriesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCategoriesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class CategoriesFragment : BaseFragment(R.layout.categories_fragment) {
    private val binding by viewBinding(CategoriesFragmentBinding::bind)

    private val viewModel: CategoriesViewModel by viewModels(
        factoryProducer = { CategoriesViewModelFactory() }
    )
}

private class CategoriesViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerCategoriesViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
