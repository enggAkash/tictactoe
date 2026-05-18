package com.engineerakash.tictactoe.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PlayerType(val type: String) : Parcelable {
    P1("p1"), P2("p2")
}