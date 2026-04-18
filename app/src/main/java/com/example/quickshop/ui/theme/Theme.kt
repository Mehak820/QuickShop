package com.example.quickshop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = NavyBlue,
    onPrimary = Color.White,
    background = SurfaceGrey,
    onBackground = TextPrimary,
    surface = CardWhite,
    onSurface = TextPrimary,
)

@Composable
fun QuickShopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}