package ru.ozh.compose.challenges.ui.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Dot() {
    Canvas(modifier = Modifier.size(2.dp)) {
        drawCircle(
            color = Color.Black,
        )
    }
}

@Composable
fun DotsColumn() {
    Column (
        modifier = Modifier.width(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Dot()
        Spacer(modifier = Modifier.height(4.dp))
        Dot()
    }
}