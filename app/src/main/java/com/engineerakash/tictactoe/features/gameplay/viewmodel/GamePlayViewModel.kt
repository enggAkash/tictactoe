package com.engineerakash.tictactoe.features.gameplay.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.engineerakash.tictactoe.R
import kotlin.math.roundToInt
import kotlin.random.Random

class GamePlayViewModel : ViewModel() {


    //onBoardBoxClicked: (Int, Int) -> Unit

    /**
     * -1 -> Not Filled Yet
     * 0 -> Zero (0)
     * 1 -> Kata (X)
     */
    val boardMatrixValue: Array<Array<MutableState<Int>>> by
    mutableStateOf(
        Array(
            3,
            { i ->
                Array(3, { j -> mutableIntStateOf(-1) })
            }
        )
    )

    val emptyBoxes by
    derivedStateOf {
        /**
         * this list will store, i and j index of empty boxes (matrix)
         */
        val list = arrayListOf<Pair<Int, Int>>()
        for (i in 0 until boardMatrixValue.size) {
            for (j in 0 until boardMatrixValue[i].size) {
                if (boardMatrixValue[i][j].value == -1) {
                    list.add(Pair(i, j))
                }
            }
        }
        list.toTypedArray()
    }

    var p1Turn by mutableStateOf(isP1Turn())


    var p1WinCounter: Int by mutableIntStateOf(0)
    var p2WinCounter: Int by mutableIntStateOf(0)

    val confettiShowDuration = 10000L

    var turnIndicatorText: String by mutableStateOf(
        "Your Turn"
    )

    /**
     * Either it's draw or someone just wins
     */
    var isBoardDirty: Boolean by mutableStateOf(false)

    /**
     * 1st -> Show Confetti
     * 2nd -> isPlayer1 wins
     */
    var showConfetti: Pair<Boolean, Boolean> by mutableStateOf(
        Pair(
            false,
            false
        )
    )

    val player2name by mutableStateOf("Bot")


    /**
     * Generates random Zero(0) and Kata(X) icon for both players
     */
    fun getP1P2IconAndIndex(): Triple<Int, Int, Int> {
        val icons = arrayOf(R.drawable.ic_zero, R.drawable.ic_kata)
        val p1Index = Random.nextDouble().roundToInt()

        return Triple(icons[p1Index], icons[1 - p1Index], p1Index)
    }

    fun isP1Turn(): Boolean {
        return true
    }

}