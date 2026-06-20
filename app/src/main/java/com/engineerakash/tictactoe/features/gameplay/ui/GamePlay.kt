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
import com.engineerakash.tictactoe.core.util.CONFETTI_SHOW_DURATION
import com.engineerakash.tictactoe.core.util.GameType
import com.engineerakash.tictactoe.features.gameplay.viewmodel.GamePlayViewModel
import com.engineerakash.tictactoe.features.gameplay.viewmodel.GamePlayViewModelFactory
import com.engineerakash.tictactoe.features.gameplay.widgets.DrawConfetti
import com.engineerakash.tictactoe.features.gameplay.widgets.ScoreCounter
import com.engineerakash.tictactoe.features.gameplay.widgets.ZeroKataBoard
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "akt"

@Composable
fun GamePlay(
    gameType: GameType,
    onBackPressed: () -> Unit
) {

    val viewModel: GamePlayViewModel = viewModel<GamePlayViewModel>(
        factory = GamePlayViewModelFactory(gameType)
    )

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

            ScoreCounter(
                p1Icon,
                p2Icon,
                viewModel.whoHasPlayedCurrentMove,
                viewModel.p1WinCounter,
                viewModel.p2WinCounter,
                viewModel.player2name
            )

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
                    viewModel.resetGame(viewModel)
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
            DrawConfetti(viewModel.showConfetti.second, CONFETTI_SHOW_DURATION)

            LaunchedEffect(Unit) {
                delay(CONFETTI_SHOW_DURATION.milliseconds)
                viewModel.showConfetti = Pair(false, false)
            }
        }
    }

}

@Preview
@Composable
fun PreviewGamePlay() {
    GamePlay(GameType.PLAY_WITH_BOT, {})
}