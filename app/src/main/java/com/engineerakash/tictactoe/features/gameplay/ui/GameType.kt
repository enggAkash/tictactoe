package com.engineerakash.tictactoe.features.gameplay.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameType(val type: String) : Parcelable {
    PLAY_SOLO("play_solo"), PLAY_WITH_FRIEND("play_with_friend")
}