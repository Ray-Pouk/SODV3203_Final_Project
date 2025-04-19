package com.example.sodv3203_final_project.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    cartItems: List<MenuItem> = listOf(),
    onContinueClicked: () -> Unit = {}
) {
    val subtotal = cartItems.sumOf { it.price }
    val tax = subtotal * 0.13  // 13% tax
    val total = subtotal + tax

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Checkout") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(NavigationRoutes.HomePage) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Cart items list
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                cartItems.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.name)
                        Text("$${"%.2f".format(item.price)}")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Totals section
            Column {
                Text("Subtotal: $${"%.2f".format(subtotal)}")
                Text("Tax: $${"%.2f".format(tax)}")
                Text(
                    "Total: $${"%.2f".format(total)}",
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )
            }

            // Continue Button
            Button(
                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue to Payment")
            }
        }
    }
}
