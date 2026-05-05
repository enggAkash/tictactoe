package com.engineerakash.tictactoe.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.engineerakash.tictactoe.R
import com.engineerakash.tictactoe.core.theme.BackgroundColor
import com.engineerakash.tictactoe.core.theme.LightModeDarkModeColor

@Composable
fun HomeScreen(openGamePlayScreen: (String) -> Unit) {

    Scaffold(

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.size(10.dp))

            TopBar()

            Spacer(modifier = Modifier.size(20.dp))

            AppLogoAndTitle()

            ActionButtons(openGamePlayScreen)

        }
    }
}

@Composable
private fun ActionButtons(openGamePlayScreen: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            onClick = {
                openGamePlayScreen("play_solo")
            }
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Filled.Person2,
                    contentDescription = "Play Solo",
                    modifier = Modifier
                        .size(30.dp)
                )

                Text(
                    "Play Solo",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
        }

        Spacer(
            modifier = Modifier.size(20.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            onClick = {
                openGamePlayScreen("play_solo")
            }
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Filled.Group,
                    contentDescription = "Play Solo",
                    modifier = Modifier
                        .size(30.dp)
                )

                Text(
                    "Play with a friend",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
        }

    }
}

@Composable
private fun AppLogoAndTitle() {
    Icon(
        painter = painterResource(R.mipmap.ic_launcher),
        contentDescription = "Logo",
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),
    )

    Text(
        stringResource(R.string.app_name),
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = Color(0xFF8CA7DF),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            imageVector = if (isSystemInDarkTheme()) Icons.Filled.DarkMode else Icons.Filled.LightMode,
            contentDescription = "",
            modifier = Modifier
                .background(LightModeDarkModeColor)
                .padding(5.dp)
        )

        Spacer(
            modifier = Modifier.weight(1.0f)
        )

        Icon(
            imageVector = if (isSystemInDarkTheme()) Icons.Outlined.Info else Icons.Outlined.Info,
            contentDescription = "",
            modifier = Modifier
                .background(LightModeDarkModeColor)
                .padding(5.dp)
        )
    }
}