package com.example.sodv3203_final_project.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Data.Orders.CartManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit = {}
) {

    val checkoutViewModel: CheckoutViewModel = viewModel()


    val cartItems by checkoutViewModel.cartItems.collectAsState()

    // Totals
    val subtotal = cartItems.sumOf { it.finalPrice * it.quantity }
    val tax = subtotal * 0.13
    val total = subtotal + tax

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Checkout") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (cartItems.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Your cart is empty",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Continue Shopping")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    cartItems.forEachIndexed { index, item ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${item.name} x${item.quantity}")
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("$${"%.2f".format(item.finalPrice * item.quantity)}")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    // Add remove button
                                    IconButton(
                                        onClick = {
                                            checkoutViewModel.removeCartItem(index)
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Remove item",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }

                            item.customizations.forEach { customization ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, bottom = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "${customization.customizationName}: ${customization.customizationValue}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        "+$${"%.2f".format(customization.customizationPrice)}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            if (item.instructions.isNotBlank()) {
                                Text(
                                    "Instructions: ${item.instructions}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }

                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Column {
                    Text("Subtotal: $${"%.2f".format(subtotal)}")
                    Text("Tax: $${"%.2f".format(tax)}")
                    Text(
                        "Total: $${"%.2f".format(total)}",
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.secondary)
                    )
                }

                Button(
                    onClick = {
                        onContinueClicked() // Continue to payment or other action
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Continue to Payment")
                }
            }
        }
    }
}