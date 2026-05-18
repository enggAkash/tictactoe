package com.engineerakash.tictactoe.core

import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.engineerakash.tictactoe.features.gameplay.ui.GamePlay
import com.engineerakash.tictactoe.core.util.GameType
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
                    type = NavType.EnumType(GameType::class.java)
                }
            )
        ) { backStackEntry ->

            val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                backStackEntry.arguments?.getParcelable<GameType>("type", GameType::class.java)
                    ?: GameType.PLAY_WITH_BOT
            } else {
                backStackEntry.arguments?.getParcelable<GameType>("type") ?: GameType.PLAY_WITH_BOT
            }

            GamePlay(gameType = type, onBackPressed = {
                navController.popBackStack()
            })

        }

    }

}