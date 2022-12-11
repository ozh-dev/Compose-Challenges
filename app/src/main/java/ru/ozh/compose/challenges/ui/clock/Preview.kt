package ru.ozh.compose.challenges.ui.clock

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Preview
@Composable
fun WaveClockPreview() {
    var time by remember { mutableStateOf(currentTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            time = currentTime()
            delay(1000)
        }
    }

    WaveClock(time)
}

@Preview
@Composable
fun DotsColumnPreview() {
    DotsColumn()
}