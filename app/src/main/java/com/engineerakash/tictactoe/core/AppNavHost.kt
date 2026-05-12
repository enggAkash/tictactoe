package com.engineerakash.tictactoe.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.engineerakash.tictactoe.features.gameplay.ui.GamePlay
import com.engineerakash.tictactoe.features.home.ui.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
    ) {

        composable(AppScreens.Home.route) {
            HomeScreen() { type ->
                navController.navigate(AppScreens.GamePlay.createRoute(type))
            }
        }

        composable(
            route = AppScreens.GamePlay.route,
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val type = backStackEntry.arguments?.getString("type") ?: "play_solo"

            GamePlay(type) {
                navController.popBackStack()
            }

        }

    }

}