package com.teknistest.todolist.repository

import com.teknistest.todolist.data.model.LoginRequest
import com.teknistest.todolist.data.model.RegisterRequest
import com.teknistest.todolist.data.remote.ApiService

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(request: LoginRequest) = apiService.login(request)
    suspend fun register(request: RegisterRequest) = apiService.register(request)
}