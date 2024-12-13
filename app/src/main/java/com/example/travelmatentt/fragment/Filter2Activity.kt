package com.example.travelmatentt.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmatentt.R
import com.example.travelmatentt.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Filter2Activity : AppCompatActivity() {

    private lateinit var destinationsRecyclerView: RecyclerView
    private lateinit var adapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter2)


        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null)


        destinationsRecyclerView = findViewById(R.id.recyclerViewRecommendations)
        destinationsRecyclerView.layoutManager = LinearLayoutManager(this)

        accessToken?.let {
            fetchDestinations(it)
        } ?: run {
            Log.e("Filter2Activity", "Token tidak ditemukan")
            Toast.makeText(this, "Token tidak ditemukan, silakan login kembali", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDestinations(token: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travelmate-ntt-1096623490059.asia-southeast2.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val jenis = "pantai" // Ganti dengan jenis yang valid
        Log.d("Filter2Activity", "Fetching destinations for jenis: $jenis")
        Log.d("Filter2Activity", "Access token: Bearer $token")

        val call = apiService.getDestinations("Bearer $token", jenis)
        adapter = DestinationAdapter(emptyList())
        destinationsRecyclerView.adapter = adapter

        call.enqueue(object : Callback<List<DestinationResponse>> {
            override fun onResponse(
                call: Call<List<DestinationResponse>>,
                response: Response<List<DestinationResponse>>
            ) {
                if (response.isSuccessful) {
                    val destinations = response.body() ?: emptyList()
                    Log.d("Filter2Activity", "Fetched ${destinations.size} destinations")
                    adapter = DestinationAdapter(destinations)
                    destinationsRecyclerView.adapter = adapter
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Filter2Activity", "Failed to fetch destinations: ${response.code()} - ${response.message()}")
                    Log.e("Filter2Activity", "Error body: $errorBody")
                    Toast.makeText(
                        this@Filter2Activity,
                        "Gagal memuat data: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()


                    adapter = DestinationAdapter(emptyList())
                    destinationsRecyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<DestinationResponse>>, t: Throwable) {
                Log.e("Filter2Activity", "Network error: ${t.localizedMessage}")
                t.printStackTrace()
                Toast.makeText(this@Filter2Activity, "Gagal memuat data: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
