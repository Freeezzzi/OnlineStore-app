package ru.freeezzzi.coursework.onlinestore.ui.mainpage.profile

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.ProfileFragmentBinding
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerProfileViewModelComponent
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.SplashActivity

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {
    private val binding by viewBinding(ProfileFragmentBinding::bind)

    private val viewModel: ProfileViewModel by viewModels(
        factoryProducer = { ProfileViewModelFactory() }
    )

    override fun initViews(view: View) {
        super.initViews(view)

        binding.logOutButton.setOnClickListener {
            viewModel.logOut()
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}

private class ProfileViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerProfileViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
