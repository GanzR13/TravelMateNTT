package com.example.travelmatentt.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelmatentt.data.preference.UserModel
import com.example.travelmatentt.data.preference.UserPreference
import com.example.travelmatentt.databinding.ActivityLoginBinding
import com.example.travelmatentt.view.main.MainActivity
import com.example.travelmatentt.view.register.RegisterActivity
import com.example.travelmatentt.view.welcome.WelcomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        userPreference = UserPreference(this)

        setupView()
        setupClickListener()
        observeViewModel()
    }

    private fun setupView() {
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
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (isValidEmail(email) && password.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        loginViewModel.loginStatus.observe(this) { isLoggedIn ->
            binding.progressBar.visibility = View.GONE
            if (isLoggedIn) {
                // Ambil data pengguna dari model atau API
                val user = UserModel(
                    email = binding.edLoginEmail.text.toString(),
                    id = "userId", // Ganti dengan ID pengguna dari respons login
                    username = "username", // Ganti dengan nama pengguna dari respons login
                    isLogin = true
                )

                // Simpan data pengguna menggunakan UserPreference
                userPreference.saveUser(user)

                // Arahkan ke MainActivity setelah login berhasil
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
