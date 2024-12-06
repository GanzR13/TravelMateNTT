package com.example.travelmatentt.view.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelmatentt.data.request.RegisterRequest
import com.example.travelmatentt.data.retrofit.ApiConfig
import com.example.travelmatentt.databinding.ActivityRegisterBinding
import com.example.travelmatentt.view.login.LoginActivity
import com.example.travelmatentt.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupClickListener()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupClickListener() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val confirmPassword = binding.edConfirmRegisterPassword.text.toString()

            if (validateInput(username, email, password, confirmPassword)) {
                registerUser(username, email, password, confirmPassword)
            }
        }
    }

    private fun validateInput(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (username.length < 3) {
            Toast.makeText(this, "Username must be at least 3 characters!", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(username: String, email: String, password: String, confirmPassword: String) {
        lifecycleScope.launch {
            try {

                val apiService = ApiConfig.getApiService()
                val registerRequest = RegisterRequest(username, email, password, confirmPassword)
                val response = apiService.register(registerRequest)

                if (response.message == "User registered successfully") {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration successful!",
                        Toast.LENGTH_SHORT
                    ).show()


                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration failed: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {

                Toast.makeText(
                    this@RegisterActivity,
                    "An error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}