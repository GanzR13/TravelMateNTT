package com.example.travelmatentt.Onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.travelmatentt.Asesment.Assesment
import com.example.travelmatentt.R
import com.example.travelmatentt.view.welcome.WelcomeActivity


class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: ImageView
    private lateinit var btnPrev: ImageView
    private lateinit var btnGetStarted: View
    private lateinit var dotContainer: LinearLayout
    private val dotViews = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        supportActionBar?.hide()

        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        btnPrev = findViewById(R.id.btnPrev)
        btnGetStarted = findViewById(R.id.btnGetStarted)
        dotContainer = findViewById(R.id.dotContainer)

        val pages = listOf(
            R.layout.layout_onboarding_page1,
            R.layout.layout_onboarding_page2,
            R.layout.layout_onboarding_page3
        )

        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun getItemCount(): Int = pages.size
            override fun getItemViewType(position: Int): Int = pages[position]
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        }

        setupDots(pages.size)


        btnNext.setOnClickListener {
            if (viewPager.currentItem < pages.size - 1) {
                viewPager.currentItem += 1
            }
        }

        btnPrev.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem -= 1
            }
        }


        btnGetStarted.setOnClickListener {

            startActivity(Intent(this, Assesment::class.java))
            finish()
        }


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnPrev.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE


                btnNext.visibility = if (position == pages.size - 1) View.INVISIBLE else View.VISIBLE


                btnGetStarted.visibility = if (position == pages.size - 1) View.VISIBLE else View.GONE

                updateDots(position)
            }
        })
    }


    private fun setupDots(count: Int) {
        dotViews.clear()
        dotContainer.removeAllViews()
        for (i in 0 until count) {
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(16, 16).apply {
                    setMargins(8, 0, 8, 0)
                }
                setBackgroundResource(if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive)
            }
            dotContainer.addView(dot)
            dotViews.add(dot)
        }
    }


    private fun updateDots(position: Int) {
        for (i in dotViews.indices) {
            dotViews[i].setBackgroundResource(if (i == position) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }
}
