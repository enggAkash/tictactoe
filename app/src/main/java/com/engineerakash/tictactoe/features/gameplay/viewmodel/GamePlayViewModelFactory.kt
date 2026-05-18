package com.engineerakash.tictactoe.features.gameplay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.engineerakash.tictactoe.core.util.GameType

class GamePlayViewModelFactory(val gameType: GameType) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return GamePlayViewModel(gameType = gameType) as T
    }
}