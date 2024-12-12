package com.teknistest.todolist.data.remote

import com.teknistest.todolist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    inline fun <reified T> getApiService() : T {
        val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.NONE) }

        val client  = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(T::class.java)
    }
}