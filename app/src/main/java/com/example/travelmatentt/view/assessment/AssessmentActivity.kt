package com.example.travelmatentt.view.assessment

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmatentt.R
import com.example.travelmatentt.view.welcome.WelcomeActivity

class AssessmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assesment)

        val touristAttractions = listOf(
            "Air Terjun", "Batu Karang", "Bukit", "Danau", "Desa Wisata",
            "Goa", "Gunung", "Pantai", "Pulau", "Sungai", "Taman",
            "Taman Nasional", "Tugu", "Wisata Alam"
        )
        val cities = listOf(
            "Alor", "Belu", "Ende", "Flores Timur", "Kota Kupang",
            "Kupang", "Lembata", "Malaka", "Manggarai", "Manggarai Barat",
            "Manggarai Timur", "Nagekeo", "Ngada", "Rote Ndao", "Sabu Raijua",
            "Sikka", "Sumba Barat", "Sumba Barat Daya", "Sumba Tengah",
            "Sumba Timur", "Timor Tengah Selatan", "Timor Tengah Utara"
        )


        val spinnerTourismType = findViewById<Spinner>(R.id.spinner_tourism_type)
        val spinnerCity = findViewById<Spinner>(R.id.spinner_city)

        spinnerTourismType.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, touristAttractions
        )
        spinnerCity.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, cities
        )

        val seekBar = findViewById<SeekBar>(R.id.seekbar_price)
        val priceLabel = findViewById<TextView>(R.id.tv_price_label)

        val priceNames = listOf("Gratis (Rp 0)", "Terjangkau (Rp 0-20K)", "Menengah (Rp 20K-50K)", "Premium (Rp >50K)")

        seekBar.max = priceNames.size - 1

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                priceLabel.text = priceNames[progress]
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        val submitButton = findViewById<Button>(R.id.btn_submit)
        submitButton.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}