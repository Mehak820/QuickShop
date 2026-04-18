package com.example.quickshop.data

import android.content.Context
import androidx.room.Room

class AppContainer(context: Context) {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "shop_db").build()
    }

    val repository: com.example.quickshop.repository.ProductRepository by lazy {
        com.example.quickshop.repository.ProductRepository(database.cartDao())
    }
}