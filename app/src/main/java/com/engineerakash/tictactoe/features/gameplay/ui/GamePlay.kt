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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.ui.HomeBar
import com.engineerakash.tictactoe.features.gameplay.viewmodel.GamePlayViewModel
import com.engineerakash.tictactoe.features.gameplay.widgets.DrawConfetti
import com.engineerakash.tictactoe.features.gameplay.widgets.ScoreCounter
import com.engineerakash.tictactoe.features.gameplay.widgets.ZeroKataBoard
import kotlinx.coroutines.delay

private const val TAG = "akt"

@Composable
fun GamePlay(
    gameType: GameType,
    onBackPressed: () -> Unit,
    viewModel: GamePlayViewModel = viewModel()
) {

    Log.d(TAG, "Game Type: ${gameType.name}")

    val p1Icon = painterResource(viewModel.p1IconResource)
    val p2Icon = painterResource(viewModel.p2IconResource)

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

            ScoreCounter(p1Icon, p2Icon, viewModel)

            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxWidth()
            )

            Text(
                viewModel.turnIndicatorText,
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

            ZeroKataBoard(viewModel)

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
                    resetGame(viewModel)
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

        if (viewModel.showConfetti.first) {
            DrawConfetti(viewModel.showConfetti.second, viewModel.confettiShowDuration)

            LaunchedEffect(Unit) {
                delay(viewModel.confettiShowDuration)
                viewModel.showConfetti = Pair(false, false)
            }
        }
    }

}


fun checkWhoWins(
    boardMatrixValue: Array<Array<MutableState<Int>>>,
    p1Index: Int,
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

fun resetGame(viewModel: GamePlayViewModel) {

    for (i in 0 until viewModel.boardMatrixValue.size) {
        for (j in 0 until viewModel.boardMatrixValue[i].size) {
            viewModel.boardMatrixValue[i][j].value = -1
        }
    }

    viewModel.showConfetti = Pair(false, false)

    viewModel.p1Turn = true

    viewModel.isBoardDirty = false

    viewModel.turnIndicatorText = if (viewModel.p1Turn) {
        "Your Turn"
    } else {
        "${viewModel.player2name}'s Turn"
    }
}

@Preview
@Composable
fun PreviewGamePlay() {
    GamePlay(GameType.PLAY_SOLO, {})
}