package ru.freeezzzi.coursework.onlinestore.ui.mainpage

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.MainPageFragmentBinding
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class MainPageFragment : BaseFragment(R.layout.main_page_fragment) {
    private val binding by viewBinding(MainPageFragmentBinding::bind)

    private val viewPagerAdapter = MainPageFragmentPagerAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainPageViewpager.isUserInputEnabled = false
        binding.mainPageViewpager.adapter = viewPagerAdapter

        binding.bottomNaviagationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_home -> {
                    binding.mainPageViewpager.setCurrentItem(0, true)
                    true
                }
                R.id.app_bar_categories -> {
                    binding.mainPageViewpager.setCurrentItem(1, true)
                    true
                }
                R.id.app_bar_cart -> {
                    binding.mainPageViewpager.setCurrentItem(2, true)
                    true
                }
                R.id.app_bar_recipes -> {
                    binding.mainPageViewpager.setCurrentItem(3, true)
                    true
                }
                R.id.app_bar_profile -> {
                    binding.mainPageViewpager.setCurrentItem(4, true)
                    true
                }
                else -> false
            }
        }
    }
}
