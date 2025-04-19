package com.example.sodv3203_final_project.ui.LoginPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Navigation.NavigationRoutes
import com.example.sodv3203_final_project.R
import com.example.sodv3203_final_project.ui.theme.TimsRed
import com.example.sodv3203_final_project.ui.theme.TimsCream

@Composable
fun LoginPageScreen(
    navController: NavController,
    viewModel: LoginPageViewModel,
    onLoginSuccess: () -> Unit = {},
    onRegisterClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginMessage by viewModel.loginMessage.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val currentUserId by viewModel.currentUserId.collectAsState()

    // Navigate to Home page when login is successful
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(NavigationRoutes.StoreLocation) {
                popUpTo(NavigationRoutes.LoadingPage) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TimsCream)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.timhortons_logo),
            contentDescription = "Tim Hortons Logo",
            modifier = Modifier
                .height(120.dp)
                .padding(bottom = 32.dp)
        )

        Text("Welcome Back!", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.onEmailChange(it) // Update ViewModel's email state
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.onPasswordChange(it) // Update ViewModel's password state
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                viewModel.onLoginClick() // Trigger login action in ViewModel
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = TimsRed,
                contentColor = Color.White
            )
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Navigate to Register page
        TextButton(onClick = { onRegisterClicked() }) {
            Text("Don't have an account? Register", color = TimsRed)
        }

        // Display login message if there is one (e.g., error)
        if (loginMessage.isNotEmpty()) {
            Text(loginMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
