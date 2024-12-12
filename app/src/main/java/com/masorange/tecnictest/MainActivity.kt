package com.masorange.tecnictest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.masorange.tecnictest.ui.screens.ImagePickerScreen
import com.masorange.tecnictest.ui.screens.LoginScreen
import com.masorange.tecnictest.ui.screens.MainScreen
import com.masorange.tecnictest.ui.screens.ImageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController() // Controlador para la navegación
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(navController)
                }
                composable("main/{accessToken}/{username}",
                    arguments = listOf(
                        navArgument("accessToken") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )) { backStackEntry ->
                    MainScreen(navController = navController,
                        accessToken = backStackEntry.arguments?.getString("accessToken") ?: "",
                        username = backStackEntry.arguments?.getString("username") ?: ""
                    )
                }
                composable("camera/{accessToken}/{username}",
                    arguments = listOf(
                        navArgument("accessToken") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )) { backStackEntry ->
                    ImageScreen(navController = navController,
                        accessToken = backStackEntry.arguments?.getString("accessToken") ?: "",
                        username = backStackEntry.arguments?.getString("username") ?: ""
                    )
                }
                composable("import/{accessToken}/{username}",
                    arguments = listOf(
                        navArgument("accessToken") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )) { backStackEntry ->
                    ImagePickerScreen(navController = navController,
                        accessToken = backStackEntry.arguments?.getString("accessToken") ?: "",
                        username = backStackEntry.arguments?.getString("username") ?: ""
                    )
                }
            }
        }
    }
}
