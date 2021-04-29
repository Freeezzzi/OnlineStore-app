package ru.freeezzzi.coursework.onlinestore.ui.loginregistration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeezzzi.coursework.onlinestore.databinding.LoginFragmentBinding
import ru.freeezzzi.coursework.onlinestore.databinding.SignUpFragmentBinding
import java.lang.IllegalArgumentException

class LoginViewPagerAdapter(
    private val registerCallback: (String, String, String, String) -> Unit,
    private val loginCallback: (String, String) -> Unit,
    private val navigateToRegister: () -> Unit,
    private val navigateToLogin: () -> Unit
) : RecyclerView.Adapter<ViewPagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        when (viewType) {
            VIEW_TYPE_REGISTER -> {
                val binding = SignUpFragmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.signUpLoginButton.setOnClickListener { navigateToLogin.invoke() }
                RegisterViewHolder(binding)
            }
            VIEW_TYPE_LOGIN -> {
                val binding = LoginFragmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.loginSignUpButton.setOnClickListener { navigateToRegister.invoke() }
                LoginViewHolder(binding)
            }
            else -> RegisterViewHolder(
                SignUpFragmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        when (holder) {
            is RegisterViewHolder -> holder.onBind(registerCallback)
            is LoginViewHolder -> holder.onBind(loginCallback)
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> VIEW_TYPE_REGISTER
            1 -> VIEW_TYPE_LOGIN
            else -> throw IllegalArgumentException("Wrong position")
        }

    companion object {
        const val VIEW_TYPE_REGISTER = 0
        const val VIEW_TYPE_LOGIN = 1
    }
}
