package com.engineerakash.tictactoe.features.gameplay.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor
import com.engineerakash.tictactoe.features.gameplay.viewmodel.GamePlayViewModel

@Composable
fun ZeroKataBoard(
    viewModel: GamePlayViewModel/*boardMatrixValue: Array<Array<MutableState<Int>>>, onBoardBoxClicked: (Int, Int) -> Unit*/
) {
    Box(modifier = Modifier.padding(horizontal = 10.dp)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightModeDarkModeColor)
        ) {
            for (i in 0 until viewModel.boardMatrixValue.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (j in 0 until viewModel.boardMatrixValue[i].size) {
                        ZeroKataBox(
                            viewModel.boardMatrixValue[i][j]
                        ) {
                            viewModel.onBoardBoxClicked(i, j)
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
