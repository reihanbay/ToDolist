package com.teknistest.todolist.data.remote

data class BaseResponse<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val message: String? = null,
    var statusCode: Int
)

