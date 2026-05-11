package com.engineerakash.tictactoe.features.gameplay.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun GamePlay(gameType: String, onBackPressed: () -> Unit) {

    //gameType -> play_solo, play_with_friend

    print("Game Type: $gameType")

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

    var p1Turn by rememberSaveable { mutableStateOf(isP1Turn()) }

    val (p1IconResource, p2IconResource, p1Index) = getP1P2IconAndIndex()
    val p1Icon = painterResource(p1IconResource)
    val p2Icon = painterResource(p2IconResource)
    val p1WinCounter: MutableState<Int> by rememberSaveable { mutableStateOf(mutableIntStateOf(0)) }
    val p2WinCounter: MutableState<Int> by rememberSaveable { mutableStateOf(mutableIntStateOf(0)) }

    val context = LocalContext.current


    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.size(10.dp))

            HomeBar()

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

            if (p1Turn) {
                Text(
                    "Your Turn",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    "Bot Turn",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )

            ZeroKataBoard(boardMatrixValue) { i, j ->
                if (boardMatrixValue[i][j].value != -1) {
                    // There is already a value, don't overwrite
                    return@ZeroKataBoard
                }

                if (p1Turn) {
                    boardMatrixValue[i][j].value = p1Index
                } else {
                    boardMatrixValue[i][j].value = 1 - p1Index
                }

                checkWhoWins(
                    boardMatrixValue,
                    p1IconResource,
                    p2IconResource,
                    p1Index,
                    p1WinCounter,
                    p2WinCounter,
                    whoWins = { indexOfWinner ->

                        if (indexOfWinner == -1) {
                            // It's a draw
                            Toast.makeText(context, "It's a draw!", Toast.LENGTH_LONG)
                                .show()

                        } else if (p1Index == indexOfWinner) {
                            // P1 wins
                            p1WinCounter.value++
                            Toast.makeText(context, "Player 1 wins", Toast.LENGTH_LONG)
                                .show()

                        } else {
                            // P2 wins
                            p2WinCounter.value++

                            Toast.makeText(context, "Player 2 wins", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                )

                p1Turn = !p1Turn
            }

        }
    }

}

@Composable
fun ZeroKataBoard(
    boardMatrixValue: Array<Array<MutableState<Int>>>, onBoardBoxClicked: (Int, Int) -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 10.dp)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightModeDarkModeColor)
        ) {
            for (i in 0 until boardMatrixValue.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (j in 0 until boardMatrixValue[i].size) {
                        ZeroKataBox(
                            boardMatrixValue[i][j]
                        ) {
                            onBoardBoxClicked(i, j)
                        }
                    }
                }

            }
        }
    }
}

/**
 * -1 -> Not Filled Yet
 * 0 -> Zero (0)
 * 1 -> Cross (X)
 */
//@Preview
@Composable
fun ZeroKataBox(
    mutableValue: MutableState<Int>, onBoxClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable(enabled = true, onClick = onBoxClicked)
            .padding(5.dp)
            .background(BackgroundColor, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {

        if (mutableValue.value == -1) {

        } else if (mutableValue.value == 0) {
            Icon(
                painter = painterResource(R.drawable.ic_zero),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = null
            )
        } else if (mutableValue.value == 1) {
            Icon(
                painter = painterResource(R.drawable.ic_kata),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = null
            )
        }

    }
}

@Composable
fun HomeBar() {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            imageVector = if (isSystemInDarkTheme()) Icons.Filled.Home else Icons.Filled.Home,
            contentDescription = "",
            modifier = Modifier
                .background(LightModeDarkModeColor)
                .padding(5.dp),
            tint = Color.White
        )
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

@Composable
private fun ScoreCounter(
    p1Icon: Painter,
    p2Icon: Painter,
    p1WinCounter: MutableState<Int>,
    p2WinCounter: MutableState<Int>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Card(
            colors = CardDefaults.cardColors(LightModeDarkModeColor),
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    p1Icon,
                    tint = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp),
                    contentDescription = "P1 Icon",
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text("You:", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = p1WinCounter.value.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

        Card(
            colors = CardDefaults.cardColors(LightModeDarkModeColor),
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    p2Icon,
                    tint = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp),
                    contentDescription = "P2 Icon",
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Bot:", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = p2WinCounter.value.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

    }
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

@Preview
@Composable
fun PreviewGamePlay() {
    GamePlay("play_solo") { }
}