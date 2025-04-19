package com.example.sodv3203_final_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Factory.LoginPageViewModelFactory
import com.example.sodv3203_final_project.Navigation.AppNavHost
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel
import com.example.sodv3203_final_project.ui.theme.SODV3203_Final_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val userDao = db.userDao()
        val loginViewModelFactory = LoginPageViewModelFactory(userDao)

        setContent {
            // Wrap the NavHost in your custom theme
            SODV3203_Final_ProjectTheme {
                val loginViewModel: LoginPageViewModel = viewModel(factory = loginViewModelFactory)
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}
