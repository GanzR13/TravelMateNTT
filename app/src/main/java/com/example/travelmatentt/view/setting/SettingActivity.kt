package com.example.travelmatentt.view.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.travelmatentt.data.preference.UserPreference
import com.example.travelmatentt.databinding.ActivitySettingBinding
import com.example.travelmatentt.view.infoaccount.InfoAccountActivity
import com.example.travelmatentt.view.welcome.WelcomeActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var settingViewModel: SettingViewModel
    private var profileImageUri: String? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        const val PREFS_NAME = "UserSettings"
    }

    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel = ViewModelProvider(this)[SettingViewModel::class.java]

        userEmail = settingViewModel.getUserEmail()

        // Load and apply the saved theme preference
        loadThemePreference()

        loadProfileImage()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        binding.tvEditProfile.setOnClickListener {
            openGallery()
        }

        binding.btnInfoAccount.setOnClickListener {
            val intent = Intent(this, InfoAccountActivity::class.java)
            startActivity(intent)
        }

        // Toggle Dark Mode button click listener
        binding.btnToggleDarkMode.setOnClickListener {
            toggleDarkMode()
        }

        // Set the toggle button state according to current mode
        updateDarkModeToggleState()
    }

    private fun toggleDarkMode() {
        // Check current theme mode
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            // Switch to light mode
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            // Switch to dark mode
            AppCompatDelegate.MODE_NIGHT_YES
        }

        // Set the new theme mode
        AppCompatDelegate.setDefaultNightMode(newMode)

        // Save the theme preference to SharedPreferences
        settingViewModel.saveThemePreference(newMode)

        // Recreate activity to apply the theme changes immediately
        recreate()

        // Update toggle state
        updateDarkModeToggleState()
    }

    private fun updateDarkModeToggleState() {
        // Check current mode and update the toggle button state
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        binding.btnToggleDarkMode.isChecked = currentMode == AppCompatDelegate.MODE_NIGHT_YES
    }

    private fun loadThemePreference() {
        // Read the theme preference from ViewModel
        val themeMode = settingViewModel.getThemePreference()

        // Apply the saved theme mode
        AppCompatDelegate.setDefaultNightMode(themeMode)

        // Update toggle state based on saved theme preference
        updateDarkModeToggleState()
    }

    private fun logoutUser() {
        settingViewModel.clearSession()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Use Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data

            AlertDialog.Builder(this).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah Anda ingin menggunakan gambar ini sebagai foto profil?")
                setPositiveButton("Ya") { _, _ ->
                    binding.profileImageView.setImageURI(imageUri)
                    saveProfileImage(imageUri.toString())
                    profileImageUri = imageUri.toString() // Update profileImageUri
                }
                setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    private fun loadProfileImage() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        userEmail?.let {
            profileImageUri = sharedPreferences.getString("$it-profileImageUri", null)
            if (profileImageUri != null) {
                Glide.with(this)
                    .load(Uri.parse(profileImageUri))
                    .into(binding.profileImageView)
            }
        }
    }

    private fun saveProfileImage(imageUri: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        userEmail?.let {
            editor.putString("$it-profileImageUri", imageUri)
            editor.apply()
            Log.d("SettingActivity", "Saved image URI: $imageUri")
        }
        val user = UserPreference(this).getUser()
        UserPreference(this).saveUser(user.copy(profileImageUri = imageUri))
    }
}