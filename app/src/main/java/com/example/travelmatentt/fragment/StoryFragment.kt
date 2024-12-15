package com.example.travelmatentt.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmatentt.R
import com.example.travelmatentt.data.retrofit.ApiService
import com.example.travelmatentt.databinding.FragmentStoryBinding
import com.example.travelmatentt.view.upload.UploadActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryBinding
    private lateinit var storiesAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflating the layout using ViewBinding
        binding = FragmentStoryBinding.inflate(inflater, container, false)

        // Initialize RecyclerView adapter
        storiesAdapter = StoryAdapter()

        // Set RecyclerView
        binding.rvStories.layoutManager = LinearLayoutManager(context)
        binding.rvStories.adapter = storiesAdapter

        // Set up FAB click listener to navigate to UploadActivity
        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}

