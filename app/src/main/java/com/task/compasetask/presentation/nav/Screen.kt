package com.task.compasetask.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.task.compasetask.presentation.screens.CartScreen
import com.task.compasetask.presentation.screens.HomeScreen
import com.task.compasetask.presentation.screens.OrdersScreen
import com.task.compasetask.presentation.screens.SignInScreen
import com.task.compasetask.presentation.screens.SignUpScreen


sealed class Screen(val route: String) {
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Orders : Screen("orders")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route,
        modifier = modifier
    ) {
        composable(Screen.SignIn.route) {
            SignInScreen(navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Cart.route) {
            CartScreen(navController)
        }
        composable(Screen.Orders.route) {
            OrdersScreen(navController)
        }
    }
}