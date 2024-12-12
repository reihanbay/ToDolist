package com.teknistest.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teknistest.todolist.data.model.LoginRequest
import com.teknistest.todolist.data.model.RegisterRequest
import com.teknistest.todolist.data.model.TokenResponse
import com.teknistest.todolist.data.remote.BaseResponse
import com.teknistest.todolist.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginRes = MutableStateFlow<BaseResponse<TokenResponse>?>(null)
    val loginRes : StateFlow<BaseResponse<TokenResponse>?> = _loginRes

    private val _registerRes = MutableStateFlow<BaseResponse<Nothing?>?>(null)
    val registerRes : StateFlow<BaseResponse<Nothing?>?> = _registerRes


    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginRes.value = repository.login(request)
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _registerRes.value = repository.register(request)
        }
    }
}