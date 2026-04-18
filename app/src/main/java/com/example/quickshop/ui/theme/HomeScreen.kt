package com.example.quickshop.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickshop.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All") + viewModel.products.map { it.category }.distinct()
    val filteredProducts = viewModel.products.filter { product ->
        val matchesSearch = searchQuery.isEmpty() ||
                product.name.contains(searchQuery, ignoreCase = true) ||
                product.description.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || product.category == selectedCategory
        matchesSearch && matchesCategory
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Brush.horizontalGradient(colors = listOf(NavyBlueDark, NavyBlue)))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("QuickShop 🛍️", color = GoldAccent, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                        Text("Find what you love", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                    }
                    BadgedBox(
                        badge = {
                            if (viewModel.cart.isNotEmpty()) {
                                Badge(containerColor = GoldAccent) {
                                    Text("${viewModel.cart.size}", color = NavyBlue, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                    }
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search products...", color = Color.White.copy(alpha = 0.6f)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.7f)) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.White.copy(alpha = 0.7f))
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = GoldAccent
                    ),
                    singleLine = true
                )

                // Category chips
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            label = { Text(category, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = GoldAccent,
                                selectedLabelColor = NavyBlue,
                                containerColor = Color.White.copy(alpha = 0.15f),
                                labelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                selectedBorderColor = GoldAccent,
                                borderColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "home")
        }
    ) { paddingValues ->
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔍", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No products found", style = MaterialTheme.typography.titleLarge, color = TextSecondary)
                    Text("Try a different search or category", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(SurfaceGrey),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        "${filteredProducts.size} Products",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                items(filteredProducts) { product ->
                    val inCart = viewModel.isInCart(product)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardWhite)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                // Emoji icon box
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(SurfaceGrey, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(product.imageEmoji, fontSize = 28.sp)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            product.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            "$${"%.2f".format(product.price)}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = SuccessGreen
                                        )
                                    }

                                    // Category badge
                                    Surface(
                                        color = NavyBlue.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Text(
                                            product.category,
                                            fontSize = 10.sp,
                                            color = NavyBlue,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                product.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Rating row
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) { i ->
                                    val filled = i < product.rating.toInt()
                                    Text(if (filled) "★" else "☆", color = GoldAccent, fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "${product.rating} (${product.reviewCount} reviews)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    if (!inCart) viewModel.addToCart(product)
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (inCart) SuccessGreen else NavyBlue,
                                    disabledContainerColor = SuccessGreen.copy(alpha = 0.7f)
                                ),
                                enabled = !inCart
                            ) {
                                Icon(
                                    if (inCart) Icons.Default.CheckCircle else Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    if (inCart) "Added to Cart" else "Add to Cart",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
