package com.engineerakash.tictactoe.features.gameplay.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.ui.HomeBar
import com.engineerakash.tictactoe.features.gameplay.widgets.DrawConfetti
import com.engineerakash.tictactoe.features.gameplay.widgets.ScoreCounter
import com.engineerakash.tictactoe.features.gameplay.widgets.ZeroKataBoard
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

private const val TAG = "akt"

@Composable
fun GamePlay(gameType: GameType, onBackPressed: () -> Unit) {

    //gameType -> play_solo, play_with_friend

    Log.d(TAG, "Game Type: ${gameType.name}")

    /**
     * -1 -> Not Filled Yet
     * 0 -> Zero (0)
     * 1 -> Kata (X)
     */
    val boardMatrixValue: Array<Array<MutableState<Int>>> by rememberSaveable {
        mutableStateOf(
            Array(
                3,
                { i ->
                    Array(3, { j -> mutableIntStateOf(-1) })
                }
            ))
    }

    var p1Turn by rememberSaveable { mutableStateOf(mutableStateOf(isP1Turn())) }

    val (p1IconResource, p2IconResource, p1Index) = getP1P2IconAndIndex()
    val p1Icon = painterResource(p1IconResource)
    val p2Icon = painterResource(p2IconResource)
    val p1WinCounter: MutableState<Int> by rememberSaveable { mutableStateOf(mutableIntStateOf(0)) }
    val p2WinCounter: MutableState<Int> by rememberSaveable { mutableStateOf(mutableIntStateOf(0)) }

    val confettiShowDuration = 10000L

    var turnIndicatorText: MutableState<String> by rememberSaveable {
        mutableStateOf(
            mutableStateOf("Your Turn")
        )
    }

    /**
     * Either it's draw or someone just wins
     */
    var isBoardDirty: MutableState<Boolean> by rememberSaveable {
        mutableStateOf(mutableStateOf(false))
    }

    /**
     * 1st -> Show Confetti
     * 2nd -> isPlayer1 wins
     */
    var showConfetti: MutableState<Pair<Boolean, Boolean>> by rememberSaveable {
        mutableStateOf(mutableStateOf(Pair(false, false)))
    }

    val player2name = rememberSaveable { mutableStateOf("Bot") }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.size(10.dp))

            HomeBar(onBackPressed)

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )

            ScoreCounter(p1Icon, p2Icon, p1WinCounter, p2WinCounter)

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )

            Text(
                turnIndicatorText.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )

            ZeroKataBoard(boardMatrixValue) { i, j ->
                if (isBoardDirty.value) {
                    // someone just won the match or, it's a dra
                    return@ZeroKataBoard
                }

                if (boardMatrixValue[i][j].value != -1) {
                    // There is already a value, don't overwrite
                    return@ZeroKataBoard
                }

                if (p1Turn.value) {
                    boardMatrixValue[i][j].value = p1Index
                } else {
                    boardMatrixValue[i][j].value = 1 - p1Index
                }

                p1Turn.value = !p1Turn.value

                turnIndicatorText.value = if (p1Turn.value) {
                    "Your Turn"
                } else {
                    "${player2name.value}'s Turn"
                }

                checkWhoWins(
                    boardMatrixValue,
                    p1IconResource,
                    p2IconResource,
                    p1Index,
                    p1WinCounter,
                    p2WinCounter,
                    whoWins = { indexOfWinner ->
                        isBoardDirty.value = true

                        if (indexOfWinner == -1) {
                            // It's a draw
                            turnIndicatorText.value = "It's a draw!"

                        } else if (p1Index == indexOfWinner) {
                            // P1 wins
                            p1WinCounter.value++

                            turnIndicatorText.value = "You won"

                            showConfetti.value = Pair(true, true)

                        } else {
                            // P2 wins
                            p2WinCounter.value++

                            turnIndicatorText.value = "${player2name.value} won"

                            showConfetti.value = Pair(true, false)
                        }
                    }
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1.0f, true)
                    .fillMaxWidth()
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                onClick = {
                    resetGame(
                        boardMatrixValue,
                        showConfetti,
                        p1Turn,
                        isBoardDirty,
                        turnIndicatorText,
                        player2name
                    )
                }
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = Icons.Filled.Autorenew,
                        contentDescription = "Reset Game",
                        modifier = Modifier
                            .size(30.dp)
                    )

                    Text(
                        "Reset Game",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                }
            }

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )
        }

        if (showConfetti.value.first) {
            DrawConfetti(showConfetti.value.second, confettiShowDuration)

            LaunchedEffect(Unit) {
                delay(confettiShowDuration)
                showConfetti.value = Pair(false, false)
            }
        }
    }

}

fun isP1Turn(): Boolean {
    return true
}

/**
 * Generates random Zero(0) and Kata(X) icon for both players
 */
fun getP1P2IconAndIndex(): Triple<Int, Int, Int> {
    val icons = arrayOf(R.drawable.ic_zero, R.drawable.ic_kata)
    val p1Index = Random.nextDouble().roundToInt()

    return Triple(icons[p1Index], icons[1 - p1Index], p1Index)
}

fun checkWhoWins(
    boardMatrixValue: Array<Array<MutableState<Int>>>,
    p1IconResource: Int,
    p2IconResource: Int,
    p1Index: Int,
    p1WinCounter: MutableState<Int>,
    p2WinCounter: MutableState<Int>,
    whoWins: (Int) -> Unit
) {
    val isAllBoxesAreFilled = isAllBoxesAreFilled(boardMatrixValue)

    val indexOfWinner = indexOfWhoWins(boardMatrixValue)

    if (indexOfWinner == -1) {
        // currently no one wins
    } else if (p1Index == indexOfWinner) {
        // P1 wins
        whoWins(indexOfWinner)
    } else {
        // P2 wins
        whoWins(indexOfWinner)
    }

    if (!isAllBoxesAreFilled) {
        return
    }

    // It's a Draw
    whoWins(-1)
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

fun resetGame(
    boardMatrixValue: Array<Array<MutableState<Int>>>,
    showConfetti: MutableState<Pair<Boolean, Boolean>>,
    p1Turn: MutableState<Boolean>,
    isBoardDirty: MutableState<Boolean>,
    turnIndicatorText: MutableState<String>,
    player2name: MutableState<String>
) {

    for (i in 0 until boardMatrixValue.size) {
        for (j in 0 until boardMatrixValue[i].size) {
            boardMatrixValue[i][j].value = -1
        }
    }

    showConfetti.value = Pair(false, false)

    p1Turn.value = true

    isBoardDirty.value = false

    turnIndicatorText.value = if (p1Turn.value) {
        "Your Turn"
    } else {
        "${player2name.value}'s Turn"
    }
}

@Preview
@Composable
fun PreviewGamePlay() {
    GamePlay(GameType.PLAY_SOLO) { }
}