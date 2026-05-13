package com.engineerakash.tictactoe.features.gameplay.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

@Composable
fun DrawConfetti(isPlayer1: Boolean, totalDurationAllottedForConfetti: Long) {

    var showCentreConfetti by rememberSaveable { mutableStateOf(false) }
    var showLeftOrRightConfetti by rememberSaveable { mutableStateOf(false) }
    var showBottomConfetti by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showLeftOrRightConfetti = true

        delay(500)
        showBottomConfetti = true

        delay(500)
        showCentreConfetti = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Center Confetti
        if (showCentreConfetti) {
            KonfettiView(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                parties = listOf(
                    Party(
                        speed = 10f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 360, // Bursts in all directions
                        colors = listOf(
                            Color.Red.toArgb(),
                            Color.Green.toArgb(),
                            Color.Blue.toArgb(),
                            Color.Yellow.toArgb(),
                            Color.Magenta.toArgb(),
                            Color.Cyan.toArgb()
                        ),
                        emitter = Emitter(duration = 3000, TimeUnit.MILLISECONDS).max(500),
                        size = listOf(Size.LARGE)
                    )
                )
            )
        }

        if (showLeftOrRightConfetti) {
            KonfettiView(
                modifier = Modifier
                    .fillMaxSize()
                    .align(if (isPlayer1) Alignment.CenterStart else Alignment.CenterEnd),
                parties = listOf(
                    Party(
                        speed = 10f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 360, // Bursts in all directions
                        colors = listOf(
                            Color.Red.toArgb(),
                            Color.Green.toArgb(),
                            Color.Blue.toArgb(),
                            Color.Yellow.toArgb(),
                            Color.Magenta.toArgb(),
                            Color.Cyan.toArgb()
                        ),
                        emitter = Emitter(duration =5000, TimeUnit.MILLISECONDS).max(500),
                        size = listOf(Size.LARGE),
                        position = Position.Relative(if (isPlayer1) 0.1 else 0.9, 0.5)
                    )
                )
            )

        }

        if (showBottomConfetti) {
            KonfettiView(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                parties = listOf(
                    Party(
                        speed = 10f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 360, // Bursts in all directions
                        colors = listOf(
                            Color.Red.toArgb(),
                            Color.Green.toArgb(),
                            Color.Blue.toArgb(),
                            Color.Yellow.toArgb(),
                            Color.Magenta.toArgb(),
                            Color.Cyan.toArgb()
                        ),
                        emitter = Emitter(duration = 3000, TimeUnit.MILLISECONDS).max(500),
                        size = listOf(Size.LARGE),
                        position = Position.Relative(0.5, 1.0)
                    )
                ),
            )

        }
    }
}
