package com.teknistest.todolist.data.remote

import com.teknistest.todolist.data.model.LoginRequest
import com.teknistest.todolist.data.model.RegisterRequest
import com.teknistest.todolist.data.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // 1. Request Token (Login)
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<TokenResponse>

    // 2. Register
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): BaseResponse<Nothing?>
}