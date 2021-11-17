package com.example.coroutineapplication.network.api

import com.example.coroutineapplication.data.People
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
    @GET("people/2")
    suspend fun getData(): Response<People>

}