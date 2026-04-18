package com.example.quickshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickshop.ui.theme.CartScreen
import com.example.quickshop.ui.theme.ConfirmationScreen
import com.example.quickshop.ui.theme.HomeScreen
import com.example.quickshop.ui.theme.LoginScreen
import com.example.quickshop.ui.theme.PaymentScreen
import com.example.quickshop.ui.theme.ProfileScreen
import com.example.quickshop.ui.theme.RegisterScreen
import com.example.quickshop.viewmodel.MainViewModel

@Composable
fun NavGraph(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController, viewModel)
        }

        composable("home") {
            HomeScreen(navController, viewModel)
        }

        composable("cart") {
            CartScreen(navController, viewModel)
        }

        composable("profile") {
            ProfileScreen(navController, viewModel)
        }

        composable("payment") {
            PaymentScreen(navController, viewModel)
        }

        composable("confirmation") {
            ConfirmationScreen(navController, viewModel)
        }
    }
}