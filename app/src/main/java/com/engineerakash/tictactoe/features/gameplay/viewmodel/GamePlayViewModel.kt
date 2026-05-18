package com.engineerakash.tictactoe.features.gameplay.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.features.gameplay.ui.checkWhoWins
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


    var p1IconResource: Int
    var p2IconResource: Int
    var p1Index: Int

    init {
        var (tempP1IconResource, tempP2IconResource, tempP1Index) = getP1P2IconAndIndex()

        p1IconResource = tempP1IconResource
        p2IconResource = tempP2IconResource
        p1Index = tempP1Index

    }

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

    fun onBoardBoxClicked(i: Int, j: Int) {
        if (isBoardDirty) {
            // someone just won the match or, it's a draw
            return
        }

        if (boardMatrixValue[i][j].value != -1) {
            // There is already a value, don't overwrite
            return
        }

        if (p1Turn) {
            boardMatrixValue[i][j].value = p1Index
        } else {
            boardMatrixValue[i][j].value = 1 - p1Index
        }

        p1Turn = !p1Turn

        turnIndicatorText = if (p1Turn) {
            "Your Turn"
        } else {
            "${player2name}'s Turn"
        }

        if (!p1Turn) {
            // Player's 2 (Bot's) turn

            val tempEmptyBox = emptyBoxes

            val boxIndex = tempEmptyBox[(Math.random() * tempEmptyBox.size).roundToInt()
                .coerceAtMost(tempEmptyBox.size - 1)]

            //todo CALL onBoxClicked()

        }

        checkWhoWins(
            boardMatrixValue,
            p1Index,
            whoWins = { indexOfWinner ->
                isBoardDirty = true

                if (indexOfWinner == -1) {
                    // It's a draw
                    turnIndicatorText = "It's a draw!"

                } else if (p1Index == indexOfWinner) {
                    // P1 wins
                    p1WinCounter++

                    turnIndicatorText = "You won"

                    showConfetti = Pair(true, true)

                } else {
                    // P2 wins
                    p2WinCounter++

                    turnIndicatorText = "${player2name} won"

                    showConfetti = Pair(true, false)
                }
            }
        )
    }

}