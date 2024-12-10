package com.example.travelmatentt.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmatentt.data.preference.UserModel
import com.example.travelmatentt.data.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(userModel: UserModel) = viewModelScope.launch {
        repository.saveSession(userModel)
    }
}