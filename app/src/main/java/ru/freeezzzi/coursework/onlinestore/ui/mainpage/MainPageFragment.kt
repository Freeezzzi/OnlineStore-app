package ru.freeezzzi.coursework.onlinestore.ui.mainpage

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.MainPageFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class MainPageFragment : BaseFragment(R.layout.main_page_fragment) {
    private val binding by viewBinding(MainPageFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNaviagationView.setupWithNavController(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container))
        binding.bottomNaviagationView.setOnNavigationItemSelectedListener { item ->
            onNavDestinationSelected(item, Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container))
        }
    }

    fun getNavController(){
        val host: NavHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment? ?: return
        val navController = host.navController
    }
}
