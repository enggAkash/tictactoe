package com.engineerakash.tictactoe.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameType(val type: String) : Parcelable {
    PLAY_WITH_BOT("play_with_bot"),
    PLAY_WITH_FRIEND_OFFLINE("play_with_friend_offline"),
    PLAY_WITH_FRIEND_ONLINE("play_with_friend_online")
}