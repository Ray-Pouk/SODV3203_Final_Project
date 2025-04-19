package com.example.sodv3203_final_project.ui.LoadingPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Navigation.NavigationRoutes
import com.example.sodv3203_final_project.R
import com.example.sodv3203_final_project.ui.theme.TimsRed
import com.example.sodv3203_final_project.ui.theme.TimsYellow

@Composable
fun LoadingPageScreen(
    navController: NavController,
    viewModel: LoadingPageViewModel
) {
    val progress by viewModel.progress.collectAsState()

    LaunchedEffect(progress) {
        if (progress >= 0.99f) {
            navController.navigate(NavigationRoutes.LoginPage) {
                popUpTo("loading") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TimsRed)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tim Hortons Logo
        Image(
            painter = painterResource(id = R.drawable.timhortons_logo),
            contentDescription = "Tim Hortons Logo",
            modifier = Modifier
                .height(250.dp)
                .padding(bottom = 16.dp)
        )

        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp),
            color = TimsYellow,
            strokeWidth = 8.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Loading...",
            color = TimsYellow,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
