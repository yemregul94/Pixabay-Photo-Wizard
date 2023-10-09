package com.moonlight.tsoft_pixabay.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.moonlight.tsoft_pixabay.R
import com.moonlight.tsoft_pixabay.data.model.UserData
import com.moonlight.tsoft_pixabay.databinding.ActivityMainBinding
import com.moonlight.tsoft_pixabay.ui.auth.AuthActivity
import com.moonlight.tsoft_pixabay.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLogin()
        setupBottomNav()
    }

    private fun observeLogin() {
        authViewModel.currentUser.observe(this) {
            if (it != authViewModel.latestUser) {
                authViewModel.latestUser = it

                if (it == null) {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    mainViewModel.getUser(it.uid)

                    if (authViewModel.checkNewUser()) {
                        val user = UserData(it.uid, "name", emptyList())
                        mainViewModel.updateUserData(user, it.uid)
                    }
                }
            }
        }
    }

    private fun setupBottomNav(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigationView
        setupWithNavController(bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val currentDestinationId = navController.currentDestination?.id
            when (item.itemId) {
                R.id.dashboardFragment -> {
                    navController.popBackStack(R.id.dashboardFragment, false)
                    true
                }
                R.id.favoritesFragment -> {
                    if (currentDestinationId != R.id.favoritesFragment) {
                        navController.navigate(R.id.favoritesFragment)
                    }
                    true
                }
                R.id.menu_logout -> {
                    authViewModel.signOut()
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.itemDetailsFragment -> {
                    binding.bottomNavigationView.menu.findItem(R.id.dashboardFragment).isChecked = true
                }
            }
        }
    }
}
