package ru.freeezzzi.coursework.onlinestore.ui.mainpage.home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.HomeFragmentBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.ViewState
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile.ProfileViewModel

class HomeFragment : BaseFragment(R.layout.home_fragment) {
    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)

    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val cartViewModel: CartViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val categoriesListAdapter = CategoriesGridListAdapter()

    //private val recentlyWatchedAdapter

    override fun initViews(view: View) {
        super.initViews(view)

        // observe
        homeViewModel.categoriesList.observe(viewLifecycleOwner, ::categoriesChanged)

        homeViewModel.getCategories()

        if (profileViewModel.user.value!!.address == null) {
            binding.homeToolbar.homeLocationText.text = getString(R.string.set_address)
        } else {
            binding.homeToolbar.homeLocationText.text = profileViewModel.user.value!!.address!!.toStringWithoutPhoneAndName()
        }
        binding.homeToolbar.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileEditAddressFragment2()
            Navigation.findNavController(binding.root).navigate(action)
        }

        // recyclerviews
        binding.homeCategoriesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2,RecyclerView.HORIZONTAL,false)
        binding.homeCategoriesRecyclerview.adapter = categoriesListAdapter

        // TODO не забыть првоеряьбь есть ли айтем в корзине при загрузке и при переходе обратно в этот фрагмент (onBackpressed)
    }

    private fun categoriesChanged(newValue: ViewState<List<Category>, String?>) {
        when (newValue) {
            is ViewState.Success -> {
                categoriesListAdapter.submitList(newValue.result)
            }
            // is ViewState.Loading ->
            is ViewState.Error -> {
                // TODO вывести ошибку
            }
        }
    }
}
