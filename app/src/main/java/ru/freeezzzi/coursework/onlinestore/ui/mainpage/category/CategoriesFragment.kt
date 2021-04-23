package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CategoriesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCategoriesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState

class CategoriesFragment : BaseFragment(R.layout.categories_fragment) {
    private val binding by viewBinding(CategoriesFragmentBinding::bind)

    private val listAdapter = CategoriesListAdapter()

    private val viewModel: CategoriesViewModel by viewModels(
        factoryProducer = { CategoriesViewModelFactory() }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = listAdapter

        viewModel.getCategories()

        viewModel.categoriesList.observe(viewLifecycleOwner, ::categoriesChanged)
    }

    private fun categoriesChanged(newValue: ViewState<List<Category>, String?>) {
        when(newValue){
            is ViewState.Success -> listAdapter.submitList(newValue.result)
            //is ViewState.Error -> //TODO вывести ошибку
        }
    }
}

private class CategoriesViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerCategoriesViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
