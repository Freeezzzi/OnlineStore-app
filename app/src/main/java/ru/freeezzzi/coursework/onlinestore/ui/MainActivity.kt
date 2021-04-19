package ru.freeezzzi.coursework.onlinestore.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.ActivityMainBinding
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.MainPageFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    /*@Inject
    lateinit var navigatorHolder: NavigatorHolder*/

    /*@Inject
    lateinit var router: Router*/

    @Inject
    lateinit var authRepository: AuthRepository

    //private val navigator = AppNavigator(this, R.id.container)

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MainPageFragment())
            .commit()

        // TODO открывать экран логина
        /*if (savedInstanceState == null) {
            if (authRepository.loadUser() == null)
                router.newRootScreen(Screens.loginFragment())
            else
                router.newRootScreen(Screens.courseListFragment())
        }*/
    }

    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        //navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    private fun setNavigator(){
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return
        val navController = host.navController
    }
}
