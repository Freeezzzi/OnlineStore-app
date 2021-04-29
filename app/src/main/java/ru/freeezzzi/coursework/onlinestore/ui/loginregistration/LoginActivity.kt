package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.checkout_fragment.*
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.LoginActivivtyBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerLoginViewModelComponent
import ru.freeezzzi.coursework.onlinestore.ui.MainActivity
import ru.freeezzzi.coursework.onlinestore.ui.ViewState

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

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.registrationResult.observe(this, ::checkRegistrationResult)
        viewModel.loginResult.observe(this, ::checkRegistrationResult)
    }

    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
    }

    private fun checkRegistrationResult(it: ViewState<Unit, String?>) {
        when (it) {
            is ViewState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is ViewState.Error -> {
                binding.progressBar.isVisible = false
                showError(it.result ?: "Unknown login error")
            }
            is ViewState.Success -> {
                binding.progressBar.isVisible = false
                launchMainActivity()
            }
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(msg: String) {
        val snackbar = Snackbar.make(binding.loginViewpager, msg, Snackbar.LENGTH_LONG)
        snackbar.view.setOnClickListener { snackbar.dismiss() }
        snackbar.show()
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
