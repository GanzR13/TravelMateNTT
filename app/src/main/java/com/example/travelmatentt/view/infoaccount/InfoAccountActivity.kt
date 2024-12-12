package com.example.travelmatentt.view.infoaccount

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmatentt.databinding.ActivityInfoAccountBinding
import com.example.travelmatentt.view.main.MainActivity

class InfoAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoAccountBinding

    private val sharedPreferencesName = "user_prefs"
    private val usernameKey = "username"
    private val emailKey = "email"
    private val passwordKey = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)

        val username = sharedPreferences.getString(usernameKey, "")
        val email = sharedPreferences.getString(emailKey, "")
        val password = sharedPreferences.getString(passwordKey, "")

        binding.etUsername.setText(username)
        binding.etEmail.setText(email)
        binding.etPassword.setText(password)

        binding.progressBar.visibility = View.GONE

        binding.btnSave.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE

            val updatedUsername = binding.etUsername.text.toString()
            val updatedPassword = binding.etPassword.text.toString()

            val editor = sharedPreferences.edit()
            editor.putString(usernameKey, updatedUsername)
            editor.putString(passwordKey, updatedPassword)
            editor.apply()

            Handler(Looper.getMainLooper()).postDelayed({

                binding.progressBar.visibility = View.GONE

                Toast.makeText(this, "Account information updated", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
}