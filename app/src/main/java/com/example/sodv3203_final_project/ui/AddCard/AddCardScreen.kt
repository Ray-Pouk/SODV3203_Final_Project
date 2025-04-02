package com.example.sodv3203_final_project.ui.AddCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.sodv3203_final_project.ui.theme.SODV3203_Final_ProjectTheme

enum class PaymentMethod {
    CREDIT_CARD, PAYPAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    onSubmit: (cardNumber: String, expiry: String, cvv: String) -> Unit,
    onSwitchToPaypal: () -> Unit,
    onBackPressed: () -> Unit = {}
) {
    var selectedMethod by remember { mutableStateOf(PaymentMethod.CREDIT_CARD) }

    // State for Credit Card Fields
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var expMonth by remember { mutableStateOf("") }
    var expYear by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Add Card") },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
                // Full-width progress bar BELOW the AppBar
                LinearProgressIndicator(
                    progress = 0.5f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .graphicsLayer(scaleX = -1f)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Payment toggle buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedMethod == PaymentMethod.CREDIT_CARD,
                        onClick = { selectedMethod = PaymentMethod.CREDIT_CARD }
                    )
                    Text("Credit Card")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedMethod == PaymentMethod.PAYPAL,
                        onClick = { selectedMethod = PaymentMethod.PAYPAL }
                    )
                    Text("PayPal")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (selectedMethod) {
                PaymentMethod.CREDIT_CARD -> CreditCardForm(
                    name = name,
                    onNameChange = { name = it },
                    number = number,
                    onNumberChange = { number = it },
                    expMonth = expMonth,
                    onExpMonthChange = { expMonth = it },
                    expYear = expYear,
                    onExpYearChange = { expYear = it },
                    cvv = cvv,
                    onCvvChange = { cvv = it }
                )
                PaymentMethod.PAYPAL -> PayPalForm()
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (selectedMethod == PaymentMethod.CREDIT_CARD) {
                        onSubmit(number, "$expMonth/$expYear", cvv)
                    } else {
                        onSwitchToPaypal()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Check Out")
            }
        }
    }
}

@Composable
fun CreditCardForm(
    name: String,
    onNameChange: (String) -> Unit,
    number: String,
    onNumberChange: (String) -> Unit,
    expMonth: String,
    onExpMonthChange: (String) -> Unit,
    expYear: String,
    onExpYearChange: (String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = number,
            onValueChange = onNumberChange,
            label = { Text("Card Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = expMonth,
                onValueChange = onExpMonthChange,
                label = { Text("MM") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = expYear,
                onValueChange = onExpYearChange,
                label = { Text("YY") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = cvv,
            onValueChange = onCvvChange,
            label = { Text("Security Code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun PayPalForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
    }
}