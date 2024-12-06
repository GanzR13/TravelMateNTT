package com.example.travelmatentt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelmatentt.databinding.FragmentHomeBinding
import com.google.android.material.search.SearchBar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var searchBar: SearchBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        searchBar = binding.searchBar

        return binding.root
    }
}