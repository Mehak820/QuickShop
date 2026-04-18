package com.example.quickshop.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quickshop.data.CartItem
import com.example.quickshop.model.Product
import com.example.quickshop.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    val products = repository.getProducts()

    val cart = mutableStateListOf<Product>()

    // --- User info from registration ---
    var registeredName = mutableStateOf("")
    var registeredEmail = mutableStateOf("")
    var registeredPassword = mutableStateOf("")

    fun saveUserInfo(name: String, email: String, password: String) {
        registeredName.value = name
        registeredEmail.value = email
        registeredPassword.value = password
    }

    fun addToCart(product: Product) {
        if (cart.none { it.id == product.id }) {
            cart.add(product)
        }
        viewModelScope.launch {
            repository.addItemToCart(
                CartItem(
                    name = product.name,
                    price = product.price,
                    description = product.description
                )
            )
        }
    }

    fun removeFromCart(product: Product) {
        cart.removeIf { it.id == product.id }
        viewModelScope.launch {
            val cartItem = CartItem(name = product.name, price = product.price, description = product.description)
            repository.removeItemFromCart(cartItem)
        }
    }

    fun cartTotal(): Double = cart.sumOf { it.price }

    fun isInCart(product: Product): Boolean = cart.any { it.id == product.id }

    fun clearCart() {
        cart.clear()
    }
}

class MainViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}