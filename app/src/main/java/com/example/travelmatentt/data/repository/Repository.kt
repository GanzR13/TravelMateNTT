package com.example.travelmatentt.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.travelmatentt.data.database.DestinationDatabase
import com.example.travelmatentt.data.preference.UserModel
import com.example.travelmatentt.data.preference.UserPreference
import com.example.travelmatentt.data.request.LoginRequest
import com.example.travelmatentt.data.request.RegisterRequest
import com.example.travelmatentt.data.response.ErrorResponse
import com.example.travelmatentt.data.response.LoginResponse
import com.example.travelmatentt.data.response.RegisterResponse
import com.example.travelmatentt.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import com.example.travelmatentt.data.result.Result

class Repository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val database: DestinationDatabase
) {
    suspend fun saveSession(userModel: UserModel) = userPreference.saveSession(userModel)

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(
        username: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {

            val registerRequest = RegisterRequest(username = username, email = email, password = password, confirmPassword = password)

            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {

            val loginRequest = LoginRequest(email = email, password = password)

            val response = apiService.login(loginRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }
}