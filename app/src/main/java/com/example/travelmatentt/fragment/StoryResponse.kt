package com.example.travelmatentt.fragment



data class Story(
    val description: String?,
    val rating: Double.Companion,
    val media: List<String>,

    )

data class CreatedAt(
    val _seconds: Long,
    val _nanoseconds: Int
)




