package ru.freeezzzi.coursework.onlinestore.ui.mainpage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.home.HomeFragment

class MainPageFragmentPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment.newInstance()
            else -> Fragment()
        }

    companion object {
        private const val ITEM_COUNT = 5
    }
}
