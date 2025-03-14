package com.example.wirelessproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Homeclass.Home.route
    ) {
        composable(
            route = Homeclass.UploadImage.route
        ) {
            UploadImage(navController)
        }
        composable(
            route = Homeclass.Home.route
        ) {
            Home(navController)
        }
        composable(
            route = Homeclass.UploadFile.route
        ) {
            FileUploadScreen(navController)
        }
    }
}