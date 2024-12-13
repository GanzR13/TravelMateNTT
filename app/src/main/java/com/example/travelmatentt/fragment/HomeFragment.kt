package com.example.travelmatentt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmatentt.R
import com.example.travelmatentt.data.retrofit.ApiService
import com.example.travelmatentt.databinding.FragmentHomeBinding
import com.example.travelmatentt.view.setting.SettingActivity
import com.example.travelmatentt.fragment.RecommendationAdapter
import com.example.travelmatentt.fragment.RecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var recommendationsRecyclerView: RecyclerView
    private lateinit var adapter: RecommendationAdapter
    private var accessToken: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        accessToken = sharedPreferences.getString("access_token", null)


        recommendationsRecyclerView = binding?.recyclerViewRecommendations ?: return
        adapter = RecommendationAdapter { recommendation ->
            val intent = Intent(requireContext(), DestinationDetailActivity::class.java).apply {
                putExtra("nama_objek", recommendation.nama_objek)
                putExtra("deskripsi", recommendation.deskripsi)
                putExtra("picture_url", recommendation.picture_url)
                putExtra("alamat", recommendation.alamat)
                putExtra("rating", recommendation.rating.toString())
                putExtra("estimasi_harga_tiket", recommendation.estimasi_harga_tiket)
            }
            startActivity(intent)
        }

        recommendationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recommendationsRecyclerView.adapter = adapter


        accessToken?.let {
            fetchDestinationRecommendations(it)
        }


        binding?.profileImageView?.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }


        binding?.searchBar?.setOnClickListener {
            Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
        }


        binding?.btnFilter?.setOnClickListener {
            Toast.makeText(requireContext(), "Filter 1 clicked", Toast.LENGTH_SHORT).show()
        }
        binding?.btnFilter2?.setOnClickListener {
            val intent = Intent(requireContext(), Filter2Activity::class.java)
            startActivity(intent)
        }

        binding?.btnFilter3?.setOnClickListener {
            Toast.makeText(requireContext(), "Filter 3 clicked", Toast.LENGTH_SHORT).show()
        }
        binding?.btnFilter4?.setOnClickListener {
            Toast.makeText(requireContext(), "Filter 4 clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDestinationRecommendations(token: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travelmate-ntt-1096623490059.asia-southeast2.run.app/") // Ganti dengan URL backend Anda
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getDestinationRecommendations("Bearer $token")

        call.enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful) {
                    val recommendations = response.body()?.recommendations ?: emptyList()
                    adapter.submitList(recommendations)
                } else {

                    Log.e("HomeFragment", "Failed to fetch recommendations: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.e("HomeFragment", "Error: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

