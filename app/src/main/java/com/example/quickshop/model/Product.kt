package com.example.quickshop.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val category: String = "General",
    val imageEmoji: String = "🛍️",
    val rating: Float = 4.0f,
    val reviewCount: Int = 100
)
