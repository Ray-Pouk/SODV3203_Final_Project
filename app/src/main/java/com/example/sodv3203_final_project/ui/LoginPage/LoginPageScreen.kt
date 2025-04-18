package com.example.sodv3203_final_project.ui.LoginPage

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
import com.example.sodv3203_final_project.R
import com.example.sodv3203_final_project.ui.theme.TimsRed
import com.example.sodv3203_final_project.ui.theme.TimsCream

@Composable
fun LoginPageScreen(
    navController: NavController,
    viewModel: LoginPageViewModel, // ✅ Accept ViewModel from AppNavHost
    onLoginSuccess: () -> Unit = {},
    onRegisterClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginMessage by viewModel.loginMessage.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

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

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.onEmailChange(it)
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.onPasswordChange(it)
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.onLoginClick()
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

        TextButton(onClick = { onRegisterClicked() }) {
            Text("Don't have an account? Register", color = TimsRed)
        }

        if (loginMessage.isNotEmpty()) {
            Text(loginMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

