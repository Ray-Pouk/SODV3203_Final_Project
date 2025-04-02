package com.example.sodv3203_final_project

import CheckoutScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.sodv3203_final_project.ui.AddCard.AddCardScreen
import com.example.sodv3203_final_project.ui.OrderConfirm.OrderConfirmationScreen
import com.example.sodv3203_final_project.ui.Product.ProductInsightScreen
import com.example.sodv3203_final_project.ui.theme.SODV3203_Final_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SODV3203_Final_ProjectTheme {
                ProductInsightScreen()
            }
        }
    }
}
