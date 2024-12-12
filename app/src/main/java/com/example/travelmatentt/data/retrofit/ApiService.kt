package com.example.travelmatentt.data.retrofit

import android.service.autofill.UserData
import com.example.travelmatentt.data.preference.UserModel
import com.example.travelmatentt.data.request.LoginRequest
import com.example.travelmatentt.data.request.RegisterRequest
import com.example.travelmatentt.data.response.LoginResponse
import com.example.travelmatentt.data.response.RegisterResponse
import com.example.travelmatentt.data.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("user")
    suspend fun user(
        @Body userRequest: UserModel
    ): UserResponse
}