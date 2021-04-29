package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.databinding.LoginActivivtyBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerLoginViewModelComponent

class LoginActivity : AppCompatActivity() {
    private val binding: LoginActivivtyBinding by viewBinding(LoginActivivtyBinding::bind)

    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = { LoginViewModelFactory() }
    )

    private val adapter = LoginViewPagerAdapter(
        registerCallback = viewModel::register,
        loginCallback = viewModel::login,
        navigateToRegister = { binding.loginViewpager.currentItem = 0 },
        navigateToLogin = { binding.loginViewpager.currentItem = 1 }
    )

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding.loginViewpager.adapter = adapter
    }
}

private class LoginViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DaggerLoginViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
