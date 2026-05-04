package com.engineerakash.tictactoe.features.gameplay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor

@Preview
@Composable
fun GamePlay() {

    /**
     * -1 -> Not Filled Yet
     * 0 -> Zero (0)
     * 1 -> Cross (X)
     */
    val boardMatrixValue = rememberSaveable {
        mutableStateOf(
            Array(
                3,
                { i ->
                    Array(3, { j -> -1 })
                }
            ))
    }

    Scaffold(

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.size(10.dp))

            HomeBar()

            Spacer(modifier = Modifier.size(20.dp))

            ZeroKataBoard(boardMatrixValue)


        }
    }

}

@Composable
fun ZeroKataBoard(boardMatrixValue: MutableState<Array<Array<Int>>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightModeDarkModeColor)
            .padding(10.dp)
    ) {

        for (i in 0 until boardMatrixValue.value.size) {

            for (j in 0 until boardMatrixValue.value[i].size) {

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
    value: Int = -1
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
            .background(BackgroundColor, shape = RectangleShape),
        contentAlignment = Alignment.Center

    ) {

        if (value == -1) {

        } else if (value == 0) {
            Icon(
                painter = painterResource(R.drawable.ic_zero),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        } else if (value == 1) {
            Icon(
                painter = painterResource(R.drawable.ic_kata),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
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