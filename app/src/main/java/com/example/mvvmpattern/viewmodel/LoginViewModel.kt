package com.example.mvvmpattern.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmpattern.repository.UserRepositoryImpl
import com.example.mvvmpattern.room.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun check(user: User): Boolean =
        when {
            TextUtils.isEmpty(user.email) -> {
                _errorMessage.value = "Please enter Email"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(user.email).matches() -> {
                _errorMessage.value = "Please enter a valid Email"
                false
            }
            TextUtils.isEmpty(user.password) -> {
                _errorMessage.value = "Please enter Password"
                false
            }
            user.password.length <= 6 -> {
                _errorMessage.value = "Please enter Password greater than 6 characters"
                false
            }
            else -> true
        }


    private fun insertUser(user: User) =
        viewModelScope.launch {
            userRepository.insertUser(user)
        }

    private suspend fun onLoadUserInfo(email: String): User? =
        withContext(viewModelScope.coroutineContext) {
            userRepository.getUserByEmail(email)
        }

    fun onLogin(loginInfo: User) =
        viewModelScope.launch {
            val loadedUser = onLoadUserInfo(loginInfo.email)

            if (loadedUser == null) {
                if (check(loginInfo)) insertUser(loginInfo)
            } else {
                if (loadedUser.password == loginInfo.password) {
                    _successMessage.value = "Login success"
                } else {
                    _errorMessage.value = "Wrong Password"
                }
            }
        }
}