package com.engineerakash.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.engineerakash.tictactoe.core.AppNavHost
import com.engineerakash.tictactoe.core.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            TicTacToeTheme {
                TicTacToeApp()
            }
        }
    }
}

@Composable
fun TicTacToeApp() {
    val navController = rememberNavController()

    AppNavHost(navController = navController)
}

@Preview
@Composable
fun TicTacToeAppPreview() {
    TicTacToeTheme {
        TicTacToeApp()
    }
}