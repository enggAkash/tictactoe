package com.engineerakash.tictactoe.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor

@Composable
fun HomeBar(onHomeClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(enabled = true, onClick = onHomeClicked)
            .padding(10.dp),
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