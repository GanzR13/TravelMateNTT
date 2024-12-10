package com.example.travelmatentt.data.retrofit

import com.example.travelmatentt.data.request.LoginRequest
import com.example.travelmatentt.data.request.RegisterRequest
import com.example.travelmatentt.data.response.LoginResponse
import com.example.travelmatentt.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}