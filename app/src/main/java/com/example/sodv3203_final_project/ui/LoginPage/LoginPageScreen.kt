package com.example.sodv3203_final_project.ui.LoadingPage

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel

@Composable
fun LoadingPageScreen(viewModel: LoginPageViewModel) {
    // Collecting the current state values
    val loginMessage by viewModel.loginMessage.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Input fields for email and password
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                // When login button is clicked, call onLoginClick
                viewModel.onEmailChange(email)
                viewModel.onPasswordChange(password)
                viewModel.onLoginClick()  // Trigger the login check
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }

        // Display login message based on the result
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = loginMessage,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        if (loginSuccess) {
            Text(text = "Login successful!", color = Color.Green)
        } else {
            Text(text = "Invalid email or password.", color = Color.Red)
        }
    }
}
