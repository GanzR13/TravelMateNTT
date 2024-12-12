package com.example.travelmatentt.data.response

data class UserResponse(
	val user: User? = null
)

data class CreatedAt(
	val nanoseconds: Int? = null,
	val seconds: Int? = null
)

data class User(
	val createdAt: CreatedAt? = null,
	val photoURL: String? = null,
	val password: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null,
	val isLogin: Boolean,
	val profileImageUri: String? = null
)

