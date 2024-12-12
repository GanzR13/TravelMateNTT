package com.example.travelmatentt.data.preference

data class UserModel(
    val email: String,
    val username : String,
    val id: String,
    val isLogin: Boolean,
    val profileImageUri: String? = null
)