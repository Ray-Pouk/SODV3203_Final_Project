package com.example.sodv3203_final_project.ui.RegisterPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sodv3203_final_project.R

@Composable
fun RegisterPageScreen(
    onRegister: (String, String, String) -> Unit = { _, _, _ -> },
    onLoginInstead: () -> Unit = {},
    viewModel: RegisterPageViewModel = viewModel()  // Correct usage of viewModel() function
) {
    val fullName by remember { viewModel.fullName }
    val email by remember { viewModel.email }
    val password by remember { viewModel.password }
    val registerState by viewModel.registerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.timhortons_logo), // Make sure this exists in res/drawable
            contentDescription = "Tim Hortons Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 24.dp)
        )

        Text("Register", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { viewModel.onFullNameChanged(it) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.registerUser()
                // You can also call `onRegister` here if needed
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onLoginInstead) {
            Text("Already have an account? Login")
        }

        // Show registration result (success or error message)
        when (val state = registerState) {
            is RegisterPageViewModel.RegisterState.Success -> {
                Text(state.message, color = MaterialTheme.colorScheme.primary)
            }
            is RegisterPageViewModel.RegisterState.Error -> {
                Text(state.message, color = MaterialTheme.colorScheme.error)
            }
            else -> { /* Idle state, no message */ }
        }
    }
}
