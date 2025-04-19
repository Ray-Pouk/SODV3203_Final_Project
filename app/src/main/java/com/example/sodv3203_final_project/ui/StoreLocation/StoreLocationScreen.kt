package com.example.sodv3203_final_project.ui.StoreLocation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Data.Store
import com.example.sodv3203_final_project.Factory.StoreLocationViewModelFactory
import com.example.sodv3203_final_project.Navigation.NavigationRoutes
import com.example.sodv3203_final_project.ui.theme.TimsRed
import com.example.sodv3203_final_project.ui.theme.TimsYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreLocationScreen(
    navController: NavController,
    viewModel: StoreLocationViewModel = viewModel(
        factory = StoreLocationViewModelFactory(
            AppDatabase.getDatabase(LocalContext.current).storeDao()
        )
    )
) {
    val stores by viewModel.stores.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedStore by viewModel.selectedStore.collectAsState()

    var showConfirmDialog by remember { mutableStateOf(false) }

    // If a store is selected and dialog confirmed, navigate to the Home page
    if (showConfirmDialog && selectedStore != null) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirm Store Selection") },
            text = {
                Text("Would you like to order from ${selectedStore?.name} at ${selectedStore?.address}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        navController.navigate(NavigationRoutes.HomePage) {
                            popUpTo(NavigationRoutes.LoginPage) { inclusive = true }
                        }
                        showConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TimsRed,
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Select a Store") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
                // Search bar
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Search by location...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = TimsRed,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )

                // Progress bar to show after search bar
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = TimsYellow
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Nearby Locations",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            items(stores) { store ->
                StoreItem(
                    store = store,
                    selected = store == selectedStore,
                    onClick = {
                        viewModel.selectStore(store)
                        showConfirmDialog = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun StoreItem(
    store: Store,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 8.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location icon
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = TimsRed,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Store details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = store.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = store.address,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = store.openHours,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (store.isOpen) MaterialTheme.colorScheme.onSurface else Color.Red
                )
            }

            // Distance
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${store.distance} km",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = TimsRed
                )

                Text(
                    text = if (store.isOpen) "Open" else "Closed",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (store.isOpen) Color.Green else Color.Red
                )
            }
        }
    }
}

