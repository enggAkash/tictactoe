package com.engineerakash.tictactoe.features.gameplay.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.util.BOT_THINKING_TIME_IN_MILLI
import com.engineerakash.tictactoe.core.util.PlayerType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

class GamePlayViewModel : ViewModel() {


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

    var whoHasPlayedCurrentMove: PlayerType? by mutableStateOf(null)


    var p1WinCounter: Int by mutableIntStateOf(0)
    var p2WinCounter: Int by mutableIntStateOf(0)

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

    fun onBoardBoxClicked(i: Int, j: Int, playerType: PlayerType) {
        if (isBoardDirty) {
            // someone just won the match or, it's a draw
            return
        }

        val whoPlayedTheLastMove = whoHasPlayedCurrentMove
        if (whoPlayedTheLastMove == playerType) {
            // You have already played, it's other player's turn
            return
        }

        whoHasPlayedCurrentMove = playerType

        if (boardMatrixValue[i][j].value != -1) {
            // There is already a value, don't overwrite
            return
        }

        if (playerType == PlayerType.P1) {
            boardMatrixValue[i][j].value = p1Index
        } else {
            boardMatrixValue[i][j].value = 1 - p1Index
        }

        val indexOfWinner = checkWhoWins(boardMatrixValue, p1Index)

        if (indexOfWinner == -1) {
            isBoardDirty = true

            // It's a draw
            turnIndicatorText = "It's a draw!"

        } else if (indexOfWinner == -2) {
            // No one wins yet, continue playing

            if (playerType == PlayerType.P1 && emptyBoxes.isNotEmpty()) {
                // Player's 2 (Bot's) turn

                val tempEmptyBox = emptyBoxes

                val boxIndex = tempEmptyBox[(Math.random() * tempEmptyBox.size).roundToInt()
                    .coerceAtMost(tempEmptyBox.size - 1)]

                viewModelScope.launch {
                    //bot's thinking state
                    delay(BOT_THINKING_TIME_IN_MILLI)
                    onBoardBoxClicked(boxIndex.first, boxIndex.second, PlayerType.P2)
                }
            }

        } else if (p1Index == indexOfWinner) {
            isBoardDirty = true

            // P1 wins
            p1WinCounter++

            turnIndicatorText = "You won"

            showConfetti = Pair(true, true)

        } else {
            isBoardDirty = true

            // P2 wins
            p2WinCounter++

            turnIndicatorText = "$player2name won"

            showConfetti = Pair(true, false)
        }


        if (!isBoardDirty) {
            //someone just won, or it's a draw, don't show this turn indicator

            turnIndicatorText = if (playerType == PlayerType.P1) {
                "${player2name}'s Turn"
            } else {
                "Your Turn"
            }
        }

    }

    fun checkWhoWins(
        boardMatrixValue: Array<Array<MutableState<Int>>>,
        p1Index: Int,
//        whoWins: (Int) -> Unit
    ) : Int {
        val isAllBoxesAreFilled = isAllBoxesAreFilled(boardMatrixValue)

        val indexOfWinner = indexOfWhoWins(boardMatrixValue)

        if (indexOfWinner == -1) {
            // currently no one wins
        } else if (p1Index == indexOfWinner) {
            // P1 wins
            return indexOfWinner
        } else {
            // P2 wins
            return indexOfWinner
        }

        if (!isAllBoxesAreFilled) {
            //no one wins
            return -2
        }

        // It's a Draw
        return -1
    }

    /**
     * 0 -> if Zero(0) wins
     * 1 -> if Kata(x) wins
     * -1 -> if it's a draw
     */
    fun indexOfWhoWins(boardMatrixValue: Array<Array<MutableState<Int>>>): Int {

        /**
         * [00] [01] [02]
         * [10] [11] [12]
         * [20] [21] [22]
         */

        /*Horizontal Matching*/
        if (boardMatrixValue[0][0].value == boardMatrixValue[0][1].value && boardMatrixValue[0][1].value == boardMatrixValue[0][2].value) {
            return boardMatrixValue[0][0].value
        } else if (boardMatrixValue[1][0].value == boardMatrixValue[1][1].value && boardMatrixValue[1][1].value == boardMatrixValue[1][2].value) {
            return boardMatrixValue[1][0].value
        } else if (boardMatrixValue[2][0].value == boardMatrixValue[2][1].value && boardMatrixValue[2][1].value == boardMatrixValue[2][2].value) {
            return boardMatrixValue[2][0].value
        }

        /*Vertical Matching*/
        if (boardMatrixValue[0][0].value == boardMatrixValue[1][0].value && boardMatrixValue[1][0].value == boardMatrixValue[2][0].value) {
            return boardMatrixValue[0][0].value
        } else if (boardMatrixValue[0][1].value == boardMatrixValue[1][1].value && boardMatrixValue[1][1].value == boardMatrixValue[2][1].value) {
            return boardMatrixValue[0][1].value
        } else if (boardMatrixValue[0][2].value == boardMatrixValue[1][2].value && boardMatrixValue[1][2].value == boardMatrixValue[2][2].value) {
            return boardMatrixValue[0][2].value
        }

        /*Diagonal Matching*/
        if (boardMatrixValue[0][0].value == boardMatrixValue[1][1].value && boardMatrixValue[1][1].value == boardMatrixValue[2][2].value) {
            return boardMatrixValue[0][0].value
        } else if (boardMatrixValue[0][2].value == boardMatrixValue[1][1].value && boardMatrixValue[1][1].value == boardMatrixValue[2][0].value) {
            return boardMatrixValue[0][2].value
        }

        return -1
    }

    fun isAllBoxesAreFilled(boardMatrixValue: Array<Array<MutableState<Int>>>): Boolean {
        for (i in 0 until boardMatrixValue.size) {
            for (j in 0 until boardMatrixValue[i].size) {
                if (boardMatrixValue[i][j].value == -1) {
                    return false
                }
            }
        }

        return true
    }

    fun resetGame(viewModel: GamePlayViewModel) {

        for (i in 0 until viewModel.boardMatrixValue.size) {
            for (j in 0 until viewModel.boardMatrixValue[i].size) {
                viewModel.boardMatrixValue[i][j].value = -1
            }
        }

        viewModel.showConfetti = Pair(false, false)

        viewModel.whoHasPlayedCurrentMove = null

        viewModel.isBoardDirty = false

        viewModel.turnIndicatorText =
            if (whoHasPlayedCurrentMove == null || whoHasPlayedCurrentMove == PlayerType.P1) {
                "Your Turn"
            } else {
                "${viewModel.player2name}'s Turn"
            }
    }

}