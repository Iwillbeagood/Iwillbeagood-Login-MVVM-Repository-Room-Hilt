package com.example.mvvmpattern.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import com.example.mvvmpattern.R
import com.example.mvvmpattern.databinding.ActivityMainBinding
import com.example.mvvmpattern.room.User
import com.example.mvvmpattern.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel

        binding.mButtonLogin.setOnClickListener {
            onLogin()
        }

        loginViewModel.successMessage.observe(this) { successMessage ->
            onLoginSuccess(successMessage)
        }

        loginViewModel.errorMessage.observe(this) { errorMessage ->
            onLoginError(errorMessage)
        }
    }

    private fun onLogin() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        val loginInfo = User(email, password)

        loginViewModel.onLogin(loginInfo)
    }

    private fun onLoginSuccess(message: String?) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    private fun onLoginError(message: String?) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}