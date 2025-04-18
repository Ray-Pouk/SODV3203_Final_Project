package com.example.sodv3203_final_project.ui.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Factory.HomePageViewModelFactory
import com.example.sodv3203_final_project.Navigation.NavigationRoutes
import com.example.sodv3203_final_project.R

@Composable
fun HomePageScreen(navController: NavController) {
    val context = LocalContext.current
    val menuCategoryDao = AppDatabase.getDatabase(context).menuCategoryDao()

    val homePageViewModel: HomePageViewModel = viewModel(
        factory = HomePageViewModelFactory(menuCategoryDao)
    )

    val menuCategories by homePageViewModel.menuCategories.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main content (LazyColumn with categories)
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                item {
                    // Banner
                    Image(
                        painter = painterResource(id = R.drawable.timhortons_logo),
                        contentDescription = "Promotional Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Text(
                        text = "📍 Find a Tim Hortons Near You!",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(menuCategories) { category ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                navController.navigate("${NavigationRoutes.ProductPageWithCategory}/${category.name}")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.timhortons_logo),
                                contentDescription = "Category Image",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = category.description)
                            }
                        }
                    }
                }
            }

            // View Cart Button
            Button(
                onClick = {
                    navController.navigate(NavigationRoutes.Checkout)
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("View Cart")
            }
        }
    }
}

