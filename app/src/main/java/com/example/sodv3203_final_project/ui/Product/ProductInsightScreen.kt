package com.example.sodv3203_final_project.ui.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInsightScreen(
    menuItem: MenuItem,
    viewModel: ProductPageViewModel,
    onBack: () -> Unit,
    onAddComplete: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("Medium") }
    var instructions by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val basePrice = menuItem.price

    // Load sizes from DB on first composition
    LaunchedEffect(menuItem.id) {
        viewModel.loadSizeOptions(menuItem.id) // Ensure we are calling the correct method
    }

    val sizeOptions by viewModel.customizations.collectAsState() // Fetch customizations

    // Fallback to default sizes if no DB data
    val options = if (sizeOptions.isNotEmpty()) {
        sizeOptions.map { it.customizationName }
    } else listOf("Small", "Medium", "Large")

    // Price adjustment logic: Small = +0, Medium = +0.60, Large = +1.20
    val sizeOffset = when (selectedOption) {
        "Small" -> 0.0
        "Medium" -> 0.60
        "Large" -> 1.20
        else -> 0.0
    }

    val finalPrice = basePrice + sizeOffset

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = menuItem.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = menuItem.category,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = menuItem.imageResId),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = menuItem.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Select Size", fontSize = 14.sp)

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    label = { Text("Size") }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Custom Instructions") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onAddComplete()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Add to Order - $${"%.2f".format(finalPrice)}")
            }
        }
    }
}
