// File: ui/LoadingPage/LoadingPageScreen.kt
package com.example.sodv3203_final_project.ui.LoadingPage

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sodv3203_final_project.R

@Composable
fun LoadingPageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.timhortons_logo),
                contentDescription = "Tim Hortons Logo",
                modifier = Modifier
                    .height(120.dp)
                    .padding(bottom = 32.dp)
            )

            Spacer(modifier = Modifier.height(200.dp))

            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 6.dp,
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                    .padding(4.dp)
            )
        }
    }
}
