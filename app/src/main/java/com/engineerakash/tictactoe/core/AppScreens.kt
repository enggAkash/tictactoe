package com.engineerakash.tictactoe.core

import com.engineerakash.tictactoe.features.gameplay.ui.GameType


sealed class AppScreens(val route: String) {

    object Home : AppScreens("home")
    object GamePlay : AppScreens("game_play/{type}") {
        fun createRoute(type: GameType): String {
            return "game_play/${type.name}"
        }
    }

}