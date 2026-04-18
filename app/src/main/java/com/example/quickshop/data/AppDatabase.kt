package com.example.quickshop.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}