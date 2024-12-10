package com.example.travelmatentt.view.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmatentt.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore

class OtpEmailActivity : AppCompatActivity() {
    private lateinit var otpEditText: TextInputEditText
    private lateinit var otpInputLayout: TextInputLayout
    private lateinit var verifikasiButton: ImageButton
    private lateinit var kirimUlangOtpButton: TextView
    private lateinit var timerTextView: TextView

    //private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var firestore: FirebaseFirestore
    private lateinit var email: String

    private var countDownTimer: CountDownTimer? = null
    private val COUNTDOWN_TIME = 60 * 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_email)

        //firebaseAuth = FirebaseAuth.getInstance()
        //firestore = FirebaseFirestore.getInstance()

        email = intent.getStringExtra("email") ?: ""

        otpEditText = findViewById(R.id.otpEditText)
        otpInputLayout = findViewById(R.id.otpInputLayout)
        verifikasiButton = findViewById(R.id.verifikasiButton)
        kirimUlangOtpButton = findViewById(R.id.kirimUlangOtpButton)
        timerTextView = findViewById(R.id.timerTextView)

        mulaiCountdown()

        verifikasiButton.setOnClickListener {

        }

        kirimUlangOtpButton.setOnClickListener {

        }
    }

    private fun mulaiCountdown() {

        kirimUlangOtpButton.isClickable = false
        kirimUlangOtpButton.setTextColor(getColor(android.R.color.darker_gray))

        countDownTimer = object : CountDownTimer(COUNTDOWN_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}