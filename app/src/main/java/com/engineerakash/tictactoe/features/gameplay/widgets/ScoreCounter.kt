package com.engineerakash.tictactoe.features.gameplay.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor
import com.engineerakash.tictactoe.core.util.PlayerType
import com.engineerakash.tictactoe.features.gameplay.viewmodel.GamePlayViewModel

@Composable
fun ScoreCounter(
    p1Icon: Painter,
    p2Icon: Painter,
    viewModel: GamePlayViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Card(
            colors = CardDefaults.cardColors(LightModeDarkModeColor),
            modifier = Modifier.padding(10.dp),
            border = if (viewModel.whoHasPlayedCurrentMove == null || viewModel.whoHasPlayedCurrentMove == PlayerType.P2) BorderStroke(
                2.dp,
                Color.White
            ) else null
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                        text = viewModel.p1WinCounter.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

        Card(
            colors = CardDefaults.cardColors(LightModeDarkModeColor),
            modifier = Modifier.padding(10.dp),
            border = if (viewModel.whoHasPlayedCurrentMove == PlayerType.P1) BorderStroke(
                2.dp,
                Color.White
            ) else null
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
                        text = viewModel.p2WinCounter.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

    }
}
