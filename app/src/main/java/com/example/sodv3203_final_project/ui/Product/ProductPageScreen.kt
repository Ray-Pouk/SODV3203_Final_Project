package com.example.sodv3203_final_project.ui.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203_final_project.R
import com.example.sodv3203_final_project.ui.theme.TimsCream

data class ProductItem(
    val name: String,
    val description: String,
    val imageResId: Int
)

val sampleProducts = listOf(
    ProductItem("Coffee", "Fresh brewed coffee", R.drawable.placeholder),
    ProductItem("Bagel", "Toasted bagel with cream cheese", R.drawable.placeholder),
    ProductItem("Breakfast Wrap", "Eggs, cheese, and sausage", R.drawable.placeholder)
)

@Composable
fun ProductPageScreen(
    products: List<ProductItem> = sampleProducts,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TimsCream)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        // Top Bar with Back Arrow and Centered Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Image(
                painter = painterResource(id = R.drawable.timhortons_logo),
                contentDescription = "Tim Hortons Logo",
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductRowItem(product)
            }
        }
    }
}

@Composable
fun ProductRowItem(product: ProductItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
