package com.example.quickshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.quickshop.data.AppContainer
import com.example.quickshop.navigation.NavGraph
import com.example.quickshop.ui.theme.QuickShopTheme
import com.example.quickshop.viewmodel.MainViewModel
import com.example.quickshop.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AppContainer(this).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickShopTheme {
                NavGraph(viewModel)
            }
        }
    }
}
