package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.RecipesFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerRecipesViewModelComponent
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState

class RecipesFragment : BaseFragment(R.layout.recipes_fragment) {
    private val binding: RecipesFragmentBinding by viewBinding(RecipesFragmentBinding::bind)

    private val viewModel: RecipesViewModel by viewModels(
        factoryProducer = { RecipesViewModelFactory() }
    )

    private val listAdapter = RecipesListAdapter(
        itemClickAction = {
            val action = RecipesFragmentDirections.actionRecipesFragmentToSingleRecipeFragment(it)
            Navigation.findNavController(binding.root).navigate(action)
        }
    )

    private var currentFilter: Int = Recipe.DIFFICULTY_ALL

    override fun initViews(view: View) {
        super.initViews(view)

        binding.recipesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.recipesRecyclerview.adapter = listAdapter

        viewModel.recipesList.observe(viewLifecycleOwner, ::recipesChanged)
        viewModel.getRecipes(currentFilter)

        fillChipGroup()
        binding.recipesToolber.salesToolbarSearch.visibility = View.INVISIBLE
        binding.recipesToolber.salesToolbarArrowback.visibility = View.INVISIBLE
        binding.recipesToolber.salesToolbarLabel.text = getString(R.string.recipes)

        binding.recipesSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getRecipes(currentFilter)
        }
    }

    fun recipesChanged(newValue: ViewState<List<Recipe>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                binding.recipesSwipeRefreshLayout.isRefreshing = false
                listAdapter.submitList(newValue.result)
            }
            is ViewState.Loading -> binding.recipesSwipeRefreshLayout.isRefreshing = true
            is ViewState.Error -> {
                binding.recipesSwipeRefreshLayout.isRefreshing = false
                listAdapter.submitList(newValue.oldvalue)
                // TODO show error
            }
        }
    }

    fun fillChipGroup() {
        val stringRes = mutableListOf<Int>(R.string.all, R.string.easy, R.string.medium, R.string.hard)
        stringRes.forEach {
            val chip = layoutInflater.inflate(R.layout.custom_chip, binding.recipesFragmentChipgroup, false) as Chip
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setEnsureMinTouchTargetSize(false)
            chip.setText(getString(it))
            chip.setOnClickListener {
                binding.recipesFragmentChipgroup.check(it.id)
            }
            binding.recipesFragmentChipgroup.addView(chip)
            if (binding.recipesFragmentChipgroup.checkedChipIds.size == 0) setChipChecked(chip)
        }
        binding.recipesFragmentChipgroup.setOnCheckedChangeListener { group, checkedId ->
            currentFilter = getOrdersType()
            viewModel.filterRecipes(currentFilter)
        }
    }

    fun getOrdersType(): Int {
        val id = binding.recipesFragmentChipgroup.checkedChipId
        val chip = binding.recipesFragmentChipgroup.findViewById<Chip>(id)
        if (chip.text.toString() == getString(R.string.all)) return Recipe.DIFFICULTY_ALL
        if (chip.text.toString() == getString(R.string.easy)) return Recipe.DIFFICULTY_EASY
        if (chip.text.toString() == getString(R.string.medium)) return Recipe.DIFFICULTY_MEDIUM
        if (chip.text.toString() == getString(R.string.hard)) return Recipe.DIFFUCULTY_HARD
        return Recipe.DIFFICULTY_ALL
    }

    fun setChipChecked(chip: Chip) {
        if (chip.text.toString() == getString(R.string.all) && currentFilter == Recipe.DIFFICULTY_ALL) binding.recipesFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.easy) && currentFilter == Recipe.DIFFICULTY_EASY) binding.recipesFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.medium) && currentFilter == Recipe.DIFFICULTY_MEDIUM) binding.recipesFragmentChipgroup.check(chip.id)
        if (chip.text.toString() == getString(R.string.hard) && currentFilter == Recipe.DIFFUCULTY_HARD) binding.recipesFragmentChipgroup.check(chip.id)
    }
}

private class RecipesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerRecipesViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
