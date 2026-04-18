package com.example.quickshop.repository

import com.example.quickshop.data.CartDao
import com.example.quickshop.data.CartItem
import com.example.quickshop.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val cartDao: CartDao) {

    fun getProducts(): List<Product> {
        return listOf(
            Product(1,  "Running Shoes",       59.99,  "Lightweight & breathable for daily runs",       "Footwear",      "👟", 4.5f, 312),
            Product(2,  "Cotton T-Shirt",       19.99,  "Soft premium cotton, relaxed everyday fit",     "Clothing",      "👕", 4.3f, 528),
            Product(3,  "Leather Backpack",     79.99,  "Durable leather with multiple compartments",   "Bags",          "🎒", 4.7f, 214),
            Product(4,  "Silver Watch",        129.99,  "Elegant stainless steel with sapphire glass",  "Accessories",   "⌚", 4.8f, 187),
            Product(5,  "Oversized Hoodie",     45.50,  "Warm fleece lining, perfect for winter",       "Clothing",      "🧥", 4.6f, 403),
            Product(6,  "UV Sunglasses",        25.00,  "100% UV400 protection, polarized lenses",      "Accessories",   "🕶️", 4.2f, 266),
            Product(7,  "Snapback Cap",         15.00,  "Adjustable fit, breathable mesh back",         "Accessories",   "🧢", 4.1f, 189),
            Product(8,  "Wireless Headphones",  89.99,  "40hr battery, active noise cancellation",      "Electronics",   "🎧", 4.9f, 731),
            Product(9,  "Slim Wallet",          22.00,  "RFID blocking, holds 8 cards",                 "Accessories",   "👛", 4.4f, 155),
            Product(10, "Yoga Mat",             35.00,  "Non-slip surface, 6mm thick cushioning",       "Sports",        "🧘", 4.6f, 298),
            Product(11, "Smart Water Bottle",   28.00,  "Temperature display, 750ml insulated",         "Sports",        "🍶", 4.5f, 342),
            Product(12, "Denim Jacket",         69.99,  "Classic stonewash finish, slim cut",           "Clothing",      "🧥", 4.3f, 176)
        )
    }

    val allCartItems: Flow<List<CartItem>> = cartDao.getAllItems()

    suspend fun addItemToCart(item: CartItem) {
        cartDao.insert(item)
    }

    suspend fun removeItemFromCart(item: CartItem) {
        cartDao.delete(item)
    }
}
