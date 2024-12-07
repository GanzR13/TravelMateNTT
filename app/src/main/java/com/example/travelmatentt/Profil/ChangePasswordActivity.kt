package com.example.travelmatentt.Profil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.travelmatentt.R

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageView
    private lateinit var titleText: TextView
    private lateinit var changePasswordLayout: LinearLayout
    private lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        themeManager = ThemeManager(this)
        changePasswordLayout = findViewById(R.id.change_password_layout)
        oldPassword = findViewById(R.id.old_password)
        newPassword = findViewById(R.id.new_password)
        confirmPassword = findViewById(R.id.confirm_password)
        saveButton = findViewById(R.id.save_password_button)
        backButton = findViewById(R.id.arrow_back_button)
        titleText = findViewById(R.id.title_change_password)


        applyTheme(themeManager.getTheme())

        backButton.setOnClickListener {
            finish()
        }


        saveButton.setOnClickListener {

        }
    }

    private fun applyTheme(theme: String) {
        if (theme == ThemeManager.THEME_DARK) {
            changePasswordLayout.setBackgroundColor(ContextCompat.getColor(this,
                R.color.dark_background
            ))
            oldPassword.setTextColor(ContextCompat.getColor(this, R.color.white))
            newPassword.setTextColor(ContextCompat.getColor(this, R.color.white))
            confirmPassword.setTextColor(ContextCompat.getColor(this, R.color.white))
            saveButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            backButton.setColorFilter(ContextCompat.getColor(this, R.color.white))
            titleText.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            changePasswordLayout.setBackgroundColor(ContextCompat.getColor(this,
                R.color.light_background
            ))
            oldPassword.setTextColor(ContextCompat.getColor(this, R.color.black))
            newPassword.setTextColor(ContextCompat.getColor(this, R.color.black))
            confirmPassword.setTextColor(ContextCompat.getColor(this, R.color.black))
            backButton.setColorFilter(ContextCompat.getColor(this, R.color.black))
            titleText.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }
}
