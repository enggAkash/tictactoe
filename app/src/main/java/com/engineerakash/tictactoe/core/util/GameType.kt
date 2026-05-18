package com.engineerakash.tictactoe.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameType(val type: String) : Parcelable {
    PLAY_SOLO("play_solo"), PLAY_WITH_FRIEND("play_with_friend")
}