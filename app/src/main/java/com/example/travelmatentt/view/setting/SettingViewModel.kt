package com.example.travelmatentt.view.setting

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun clearSession() {
        sharedPreferences.edit().apply {
            remove("access_token")
            remove("refresh_token")
            apply()
        }
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("email", null)
    }

    fun saveThemePreference(themeMode: Int) {
        sharedPreferences.edit().apply {
            putInt("theme", themeMode)
            apply()
        }
    }

    // Fungsi untuk mendapatkan preferensi tema (light/dark mode)
    fun getThemePreference(): Int {
        return sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_NO) // Default ke light mode
    }
}

