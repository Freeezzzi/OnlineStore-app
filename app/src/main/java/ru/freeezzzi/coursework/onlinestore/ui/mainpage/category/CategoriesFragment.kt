package ru.freeezzzi.coursework.onlinestore.ui.mainpage.category

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.CategoriesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCategoriesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.hideKeyboard

class CategoriesFragment : BaseFragment(R.layout.categories_fragment) {
    private val binding by viewBinding(CategoriesFragmentBinding::bind)

    private val listAdapter = CategoriesListAdapter()

    private val viewModel: CategoriesViewModel by viewModels(
        factoryProducer = { CategoriesViewModelFactory() }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        setUpCardView()

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = listAdapter
        binding.categoriesRefreshLayout.setOnRefreshListener { viewModel.getCategories() }

        viewModel.getCategories()

        viewModel.categoriesList.observe(viewLifecycleOwner, ::categoriesChanged)
    }

    private fun categoriesChanged(newValue: ViewState<List<Category>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                listAdapter.submitList(newValue.result)
                binding.categoriesRefreshLayout.isRefreshing = false
            }
            is ViewState.Loading -> binding.categoriesRefreshLayout.isRefreshing = true
            is ViewState.Error -> {
                // TODO вывести ошибку
                binding.categoriesRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun performSearch() {
        view?.hideKeyboard()
        val symbol = binding.searchBarEditText.text.toString()
        if (symbol.isBlank()) {
            binding.searchBarEditText.setText("")
            return
        }
        viewModel.searchAction(symbol) // отправим запрос на сервер
    }

    private fun setUpCardView() {
        binding.searchBarDeleteIcon.setOnClickListener {
            binding.searchBarEditText.setText("")
        }
        binding.searchBarEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length ?: 0 > 0 && binding.searchBarDeleteIcon.visibility == View.INVISIBLE) {
                    binding.searchBarDeleteIcon.visibility = View.VISIBLE
                } else if (p0?.length ?: 0 == 0) {
                    binding.searchBarDeleteIcon.visibility = View.INVISIBLE
                    viewModel.clearSearchAction()
                }
                if (p0?.length ?: 0> 0) {
                    viewModel.searchAction(p0.toString())
                }
            }
        })
        binding.searchBarEditText.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER)
            ) {
                // Perform action on key press
                performSearch()
                true
            }
            false
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
