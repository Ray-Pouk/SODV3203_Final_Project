package com.example.sodv3203_final_project.Navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Factory.ProductPageViewModelFactory

import com.example.sodv3203_final_project.ui.AddCard.AddCardScreen
import com.example.sodv3203_final_project.ui.HomePage.HomePageScreen
import com.example.sodv3203_final_project.ui.LoadingPage.LoadingPageScreen
import com.example.sodv3203_final_project.ui.LoadingPage.LoadingPageViewModel
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageScreen
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel
import com.example.sodv3203_final_project.ui.OrderConfirm.OrderConfirmationScreen
import com.example.sodv3203_final_project.ui.Product.ProductInsightScreen
import com.example.sodv3203_final_project.ui.Product.ProductPageScreen
import com.example.sodv3203_final_project.ui.Product.ProductPageViewModel
import com.example.sodv3203_final_project.ui.RegisterPage.RegisterPageScreen
import com.example.sodv3203_final_project.ui.StoreLocation.StoreLocationScreen
import com.example.sodv3203_final_project.ui.checkout.CheckoutScreen
import com.example.sodv3203_final_project.ui.checkout.CheckoutViewModel
import kotlinx.coroutines.delay

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    navController: NavHostController,
    loginViewModel: LoginPageViewModel
) {
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(NavigationRoutes.HomePage) {
                popUpTo(NavigationRoutes.LoadingPage) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LoadingPage
    ) {
        composable(NavigationRoutes.LoadingPage) {
            val loadingViewModel: LoadingPageViewModel = viewModel()
            LoadingPageScreen(viewModel = loadingViewModel, navController = navController)
        }
        composable(NavigationRoutes.RegisterPage) {
            RegisterPageScreen(navController = navController)
        }
        composable(NavigationRoutes.HomePage) {
            HomePageScreen(navController = navController)
        }

        composable(
            route = "${NavigationRoutes.ProductPageWithCategory}/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val viewModel: ProductPageViewModel = viewModel(
                factory = ProductPageViewModelFactory(
                    db.menuItemDao(),
                    db.orderCustomizationDao()
                )
            )

            val category = backStackEntry.arguments?.getString("category") ?: ""

            LaunchedEffect(category) {
                Log.d("ProductPageNav", "Category: $category")
                viewModel.fetchMenuItemsByCategory(category)
            }


            ProductPageScreen(viewModel = viewModel, navController = navController, categoryName = category)
        }

        composable(
            route = "${NavigationRoutes.ProductInsight}/{menuItemId}",
            arguments = listOf(navArgument("menuItemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val viewModel: ProductPageViewModel = viewModel(
                factory = ProductPageViewModelFactory(
                    db.menuItemDao(),
                    db.orderCustomizationDao()
                )
            )

            val menuItemId = backStackEntry.arguments?.getInt("menuItemId") ?: 0
            val menuItems by viewModel.menuItems.collectAsState()
            val menuItem = menuItems.find { it.id == menuItemId }

            LaunchedEffect(menuItems.isEmpty()) {
                if (menuItems.isEmpty()) {
                    viewModel.fetchMenuItems()
                }
            }

            when {
                menuItem != null -> {
                    ProductInsightScreen(
                        menuItem = menuItem,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onAddComplete = {
                            navController.navigate(NavigationRoutes.HomePage) {
                                popUpTo(NavigationRoutes.HomePage) { inclusive = true }
                            }
                        }
                    )
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Menu item not found.")
                    }
                }
            }
        }


        composable(NavigationRoutes.LoginPage) {
            LoginPageScreen(
                navController = navController,
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.HomePage)
                },
                onRegisterClicked = {
                    navController.navigate(NavigationRoutes.RegisterPage)
                }
            )
        }

        composable(NavigationRoutes.Checkout) {
            val checkoutViewModel: CheckoutViewModel = viewModel()
            val cartItems by checkoutViewModel.cartItems.collectAsState()

            CheckoutScreen(
                navController = navController,
                onContinueClicked = {
                    navController.navigate(NavigationRoutes.AddCard)
                }
            )
        }

        composable(NavigationRoutes.AddCard) {
            val checkoutViewModel: CheckoutViewModel = viewModel()
            AddCardScreen(
                onSubmit = { cardNumber, expiry, cvv ->
                    navController.navigate(NavigationRoutes.OrderConfirmation)
                },
                onSwitchToPaypal = {
                    navController.navigate(NavigationRoutes.OrderConfirmation)
                },
                onBackPressed = {
                    navController.popBackStack()
                },
                onPaymentSuccess = {
                    checkoutViewModel.clearCart()
                }
            )
        }

        composable(NavigationRoutes.OrderConfirmation) {
            OrderConfirmationScreen(
                onReturnHome = {
                    navController.navigate(NavigationRoutes.HomePage) {
                        popUpTo(NavigationRoutes.HomePage) { inclusive = true }
                    }
                }
            )
        }

        composable(NavigationRoutes.StoreLocation) {
            StoreLocationScreen(navController = navController)
        }

    }
}

@Composable
fun Text(s: String) {
    androidx.compose.material3.Text(
        text = s,
        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
    )
}
