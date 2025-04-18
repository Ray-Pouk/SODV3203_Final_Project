package com.example.sodv3203_final_project.Navigation

import CheckoutScreen
import CheckoutViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Factory.CheckoutViewModelFactory
import com.example.sodv3203_final_project.Factory.ProductPageViewModelFactory
import com.example.sodv3203_final_project.Navigation.NavigationRoutes
import com.example.sodv3203_final_project.ui.AddCard.AddCardScreen
import com.example.sodv3203_final_project.ui.HomePage.HomePageScreen
import com.example.sodv3203_final_project.ui.LoadingPage.LoadingPageScreen
import com.example.sodv3203_final_project.ui.LoadingPage.LoadingPageViewModel
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageScreen
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel
import com.example.sodv3203_final_project.ui.Product.ProductInsightScreen
import com.example.sodv3203_final_project.ui.Product.ProductPageScreen
import com.example.sodv3203_final_project.ui.Product.ProductPageViewModel
import com.example.sodv3203_final_project.ui.RegisterPage.RegisterPageScreen
import kotlinx.coroutines.delay

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
            val loadingViewModel: LoadingPageViewModel = viewModel() // Use the correct ViewModel here
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
            val menuItem = viewModel.menuItems.collectAsState().value.find { it.id == menuItemId }

            if (menuItem != null) {
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
        }

        composable(NavigationRoutes.LoginPage) {
            LoginPageScreen(
                navController = navController,
                viewModel = loginViewModel, // ✅ Inject the existing instance
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.HomePage)
                },
                onRegisterClicked = {
                    navController.navigate(NavigationRoutes.RegisterPage)
                }
            )
        }

        composable(NavigationRoutes.Checkout) {
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val viewModel: CheckoutViewModel = viewModel(
                factory = CheckoutViewModelFactory(
                    db.orderItemDao(),
                    db.menuItemDao()
                )
            )

            CheckoutScreen(
                navController = navController,
                cartItems = viewModel.cartItems,
                onContinueClicked = {
                    // You can add a route for payment later
                }
            )

        }

        composable(NavigationRoutes.AddCard) {
            AddCardScreen(
                onSubmit = { cardNumber, expiry, cvv ->
                    // Handle submission here, maybe navigate to confirmation screen
                },
                onSwitchToPaypal = {
                    // Handle PayPal option
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }


    }
}

