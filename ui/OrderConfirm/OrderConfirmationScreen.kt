package com.example.sodv3203_final_project.ui.OrderConfirm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203_final_project.R
import androidx.compose.ui.tooling.preview.Preview
import com.example.sodv3203_final_project.ui.theme.SODV3203_Final_ProjectTheme

@Composable
fun OrderConfirmationScreen(
    onReturnHome: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Optional: Add a confirmation image/icon
        // Image(painter = painterResource(id = R.drawable.checkmark), contentDescription = null)

        Text(
            text = "Order Confirmed!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Thank you for your order.\nYour food will be ready soon!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReturnHome,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Return to Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderConfirmationPreview() {
    SODV3203_Final_ProjectTheme {
        OrderConfirmationScreen()
    }
}
