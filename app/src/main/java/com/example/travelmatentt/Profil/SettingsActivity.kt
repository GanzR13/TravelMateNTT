package com.example.travelmatentt.Profil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.travelmatentt.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeManager: ThemeManager
    private lateinit var themeSwitch: Switch
    private lateinit var backButton: ImageView
    private lateinit var profileText: TextView
    private lateinit var nama: TextView
    private lateinit var settingsLayout: View
    private lateinit var changePasswordLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        themeManager = ThemeManager(this)
        settingsLayout = findViewById(R.id.settings_layout)
        themeSwitch = findViewById(R.id.switch_theme)
        backButton = findViewById(R.id.arrow_back_button)
        profileText = findViewById(R.id.profile_name)
        nama = findViewById(R.id.nama)
        changePasswordLayout = findViewById(R.id.ll_change_password)


        applyTheme(themeManager.getTheme())

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            themeManager.setTheme(if (isChecked) ThemeManager.THEME_DARK else ThemeManager.THEME_LIGHT)
            applyTheme(themeManager.getTheme())
        }


        backButton.setOnClickListener {
            finish()
        }

        profileText.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        changePasswordLayout.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun applyTheme(theme: String) {
        if (theme == ThemeManager.THEME_DARK) {
            settingsLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_background))
            nama.setTextColor(ContextCompat.getColor(this, R.color.white))
            profileText.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            settingsLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_background))
            nama.setTextColor(ContextCompat.getColor(this, R.color.black))
            profileText.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }
}
