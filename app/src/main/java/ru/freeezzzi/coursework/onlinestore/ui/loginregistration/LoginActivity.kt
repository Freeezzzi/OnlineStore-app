package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.LoginActivivtyBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerLoginViewModelComponent

class LoginActivity : AppCompatActivity() {
    private val binding: LoginActivivtyBinding by viewBinding(LoginActivivtyBinding::bind)

    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = { LoginViewModelFactory() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activivty)

        binding.loginViewpager.adapter = LoginViewPagerAdapter(
            registerCallback = viewModel::register,
            loginCallback = viewModel::login,
            navigateToRegister = { binding.loginViewpager.currentItem = 0 },
            navigateToLogin = { binding.loginViewpager.currentItem = 1 }
        )
    }

    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
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
