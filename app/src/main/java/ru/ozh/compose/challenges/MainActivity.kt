package ru.ozh.compose.challenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.ozh.compose.challenges.ui.switch.SwitchDaN
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengesTheme {
//            GridScreen()
                SwitchScreen()
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
        
//        Switch(checked = , onCheckedChange = )
    }
}