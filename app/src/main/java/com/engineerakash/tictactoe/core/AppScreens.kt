package com.engineerakash.tictactoe.core


sealed class AppScreens(val route: String) {

    object Home : AppScreens("home")
    object GamePlay : AppScreens("game_play/{type}") {
        fun createRoute(type: String): String {
            return "game_play/$type"
        }
    }

}