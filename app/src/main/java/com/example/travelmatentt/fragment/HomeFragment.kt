package com.example.travelmatentt.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelmatentt.R
import com.example.travelmatentt.data.preference.UserPreference
import com.example.travelmatentt.databinding.FragmentHomeBinding
import com.example.travelmatentt.view.setting.SettingActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var greetingTextView: TextView
    private lateinit var userPreferences: UserPreference
    private var profileImageUri: String? = null
    private var userEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.profileImageView?.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        greetingTextView = view.findViewById(R.id.greetingTextView)
        userPreferences = UserPreference(requireContext())

        lifecycleScope.launch {
            val user = userPreferences.getUser()
            val userName = user.username
            userEmail = user.email

            Log.d("HomeFragment", "User email: $userEmail")

            val greetingMessage = getString(R.string.greeting_message, userName)
            greetingTextView.text = greetingMessage

            loadProfileImage()
        }
    }

    private fun loadProfileImage() {
        val user = userPreferences.getUser()
        val profileImageUri = user.profileImageUri
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userEmail?.let {
            this.profileImageUri = sharedPreferences.getString("$it-profileImageUri", null)
            if (!profileImageUri.isNullOrEmpty()) {

                Glide.with(this)
                    .load(Uri.parse(profileImageUri))
                    .placeholder(R.drawable.travel_mate_logo)
                    .fitCenter()
                    .into(binding?.profileImageView!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

