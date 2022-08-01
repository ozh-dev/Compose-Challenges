package ru.ozh.compose.challenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ozh.compose.challenges.ui.grid.CrossLine
import ru.ozh.compose.challenges.ui.grid.Icon
import ru.ozh.compose.challenges.ui.grid.Icons.appleIcons
import ru.ozh.compose.challenges.ui.grid.WatchGridLayout
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengesTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    WatchGridLayout(
                        modifier = Modifier
                            .size(300.dp)
                            .background(Color.Black),
                        rowItemsCount = 5,
                        itemSize = 80.dp
                    ) {
                        appleIcons.forEach { res -> Icon(res = res) }
                    }
                }
            }
        }
    }
}