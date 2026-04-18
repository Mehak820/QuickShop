package com.example.quickshop.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickshop.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController, viewModel: MainViewModel) {
    var fullName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secure Payment", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyBlue)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceGrey)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Total Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyBlue)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Order Total", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        Text(
                            "$${"%.2f".format(viewModel.cartTotal())}",
                            color = GoldAccent,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("${viewModel.cart.size} items", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        Text("Free Shipping", color = Color(0xFF81C784), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Card Details
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CreditCard, contentDescription = null, tint = NavyBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Card Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyBlue)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it; error = "" },
                        label = { Text("Full Name on Card") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = {
                            if (it.length <= 16 && it.all { c -> c.isDigit() }) {
                                cardNumber = it; error = ""
                            }
                        },
                        label = { Text("Card Number") },
                        placeholder = { Text("0000 0000 0000 0000") },
                        leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = expiryDate,
                            onValueChange = {
                                if (it.length <= 5) { expiryDate = it; error = "" }
                            },
                            label = { Text("MM/YY") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = cvv,
                            onValueChange = {
                                if (it.length <= 3 && it.all { c -> c.isDigit() }) {
                                    cvv = it; error = ""
                                }
                            },
                            label = { Text("CVV") },
                            placeholder = { Text("123") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    if (error.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(error, color = ErrorRed, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Security note
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Your payment info is encrypted & secure", fontSize = 12.sp, color = TextSecondary)
            }

            Button(
                onClick = {
                    when {
                        fullName.isBlank() -> error = "Please enter the name on your card"
                        cardNumber.length < 16 -> error = "Please enter a valid 16-digit card number"
                        expiryDate.isBlank() -> error = "Please enter expiry date"
                        cvv.length < 3 -> error = "Please enter a valid CVV"
                        else -> navController.navigate("confirmation")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CONFIRM & PAY  $${"%.2f".format(viewModel.cartTotal())}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
