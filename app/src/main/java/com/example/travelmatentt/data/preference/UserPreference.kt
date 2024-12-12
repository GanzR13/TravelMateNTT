package com.example.travelmatentt.data.preference

import android.content.Context

class UserPreference(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(user: UserModel) {
        val editor = prefs.edit()
        editor.putString("email", user.email)
        editor.putString("id", user.id)
        editor.putString("username", user.username)
        editor.putBoolean("isLogin", user.isLogin)
        editor.putString("profileImageUri", user.profileImageUri)
        editor.apply()
    }

    fun getUser(): UserModel {
        val email = prefs.getString("email", null)
        val id = prefs.getString("id", null)
        val username = prefs.getString("username", "")
        val isLogin = prefs.getBoolean("isLogin", false)
        val profileImageUri = prefs.getString("profileImageUri", null)

        return UserModel(
            username = username ?: "",
            email = email ?: "",
            id = id ?: "",
            isLogin = isLogin,
            profileImageUri = profileImageUri
        )
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("isLogin", false)
    }
}
