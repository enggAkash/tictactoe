package com.engineerakash.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineerakash.tictactoe.core.theme.TicTacToeTheme
import com.engineerakash.tictactoe.features.gameplay.ui.GamePlay
import com.engineerakash.tictactoe.features.home.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController)
                }
                composable("game_play/{game_type}") { backStackEntry ->
                    val gameType = backStackEntry.arguments?.getString("game_type") ?: "play_solo"
                    GamePlay(navController, gameType)
                }
            }

            TicTacToeTheme {
                HomeScreen(navController)
            }
        }
    }
}