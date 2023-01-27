package ru.ozh.compose.challenges

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay
import ru.ozh.compose.challenges.ui.GridScreen
import ru.ozh.compose.challenges.ui.clock.WaveClock
import ru.ozh.compose.challenges.ui.clock.currentTime
import ru.ozh.compose.challenges.ui.switch.SwitchDaN
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengesTheme {
//            GridScreen()
//                SwitchScreen()
                WaveClockScreen()

            }
        }
    }
}


@Composable
fun SwitchScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var checked by remember {
            mutableStateOf(true)
        }
        SwitchDaN(
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
    }
}

@Composable
fun WaveClockScreen() {
    var time by remember { mutableStateOf(currentTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            time = currentTime()
            delay(1000)
            Log.d("WaveClockScreen", "$time ${time.hashCode()}")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().scale(2.5f),
        contentAlignment = Alignment.Center
    ) {
        WaveClock(time)
    }

}