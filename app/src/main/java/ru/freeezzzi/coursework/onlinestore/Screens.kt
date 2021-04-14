package ru.freeezzzi.coursework.onlinestore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.home.HomeFragment

object Screens {
    fun homeFragment(): FragmentScreen =
        FragmentScreen(
            fragmentCreator = FragmentCreator(HomeFragment.newInstance())
        )

    class FragmentCreator(private val fragment: Fragment) : Creator<FragmentFactory, Fragment> {
        override fun create(argument: FragmentFactory): Fragment =
            fragment
    }
}