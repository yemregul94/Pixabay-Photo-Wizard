package com.moonlight.tsoft_pixabay.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moonlight.tsoft_pixabay.databinding.ActivityAuthBinding
import com.moonlight.tsoft_pixabay.ui.main.MainActivity
import com.moonlight.tsoft_pixabay.ui.onboard.OnboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkFirstLaunch()
        observeLogin()
    }

    private fun checkFirstLaunch() {
        if(authViewModel.checkFirstLaunch()){
            authViewModel.updateFirstLaunch()
            val intent = Intent(this, OnboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observeLogin(){
        authViewModel.currentUser.observe(this){
            if(it != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}