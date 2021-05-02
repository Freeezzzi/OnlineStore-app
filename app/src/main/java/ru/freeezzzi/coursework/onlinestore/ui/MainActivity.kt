package ru.freeezzzi.coursework.onlinestore.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.ActivityMainBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by viewModels { viewModelFactory }

    private var bottomNavManager: BottomNavManager? = null

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationManager()

        cartViewModel.initializeCart()
        cartViewModel.cartList.observe(this, ::cartChanged)
    }

    fun cartChanged(newValue: List<Product>) {
        var sum = 0
        newValue.forEach {
            sum += it.countInCart
        }
        setBadge(sum)
    }

    fun setBadge(itemsCount: Int) {
        val badge = binding.bottomNaviagationView.getOrCreateBadge(R.id.navigation_cart)
        badge.maxCharacterCount = 3
        badge.backgroundColor = applicationContext.resources.getColor(R.color.red)
        if (itemsCount == 0) {
            badge.isVisible = false
            badge.clearNumber()
        } else {
            badge.isVisible = true
            badge.number = itemsCount
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bottomNavManager?.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bottomNavManager?.onRestoreInstanceState(savedInstanceState)
        setupNavigationManager()
    }

    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
    }

    private fun setupNavigationManager() {
        bottomNavManager?.setupNavController() ?: kotlin.run {
            bottomNavManager = BottomNavManager(
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_fragment_container,
                bottomNavigationView = findViewById(R.id.bottom_naviagation_view)
            )
        }
    }

    override fun onBackPressed() {
        if (bottomNavManager?.onBackPressed() == false) super.onBackPressed()
    }
}
