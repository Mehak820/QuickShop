package com.example.quickshop.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickshop.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {
    val userName = viewModel.registeredName.value.ifBlank { "Guest" }
    val userEmail = viewModel.registeredEmail.value.ifBlank { "No email provided" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Account", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyBlue)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "profile")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceGrey)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(colors = listOf(NavyBlue, NavyBlueLight)))
                    .padding(vertical = 32.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(GoldAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.take(1).uppercase(),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyBlue
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = userName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = userEmail,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Account Information",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column {
                    ProfileMenuItem(icon = Icons.Default.Person, label = "Full Name", subtitle = userName)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = SurfaceGrey)
                    ProfileMenuItem(icon = Icons.Default.Email, label = "Email Address", subtitle = userEmail)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = SurfaceGrey)
                    ProfileMenuItem(icon = Icons.Default.Lock, label = "Password", subtitle = "••••••••••••")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Settings",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column {
                    ProfileMenuItem(icon = Icons.Default.Notifications, label = "Notifications", subtitle = "Manage your alerts")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = SurfaceGrey)
                    ProfileMenuItem(icon = Icons.Default.Help, label = "Support", subtitle = "Get help with your account")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout Account", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, label: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(NavyBlue.copy(alpha = 0.08f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = NavyBlue, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 12.sp, color = TextSecondary)
            Text(subtitle, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary.copy(alpha = 0.5f))
    }
}