package com.example.travelmatentt.Profil

import android.content.Context
import android.content.SharedPreferences

class ThemeManager(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
    }

    fun setTheme(theme: String) {
        preferences.edit().putString("theme", theme).apply()
    }

    fun getTheme(): String {
        return preferences.getString("theme", THEME_LIGHT) ?: THEME_LIGHT
    }
}

