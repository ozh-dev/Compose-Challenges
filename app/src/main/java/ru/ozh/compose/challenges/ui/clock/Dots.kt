package ru.ozh.compose.challenges.ui.clock

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Dot(
    color: Color
) {
    Canvas(modifier = Modifier.size(2.dp)) {
        drawCircle(
            color = color,
        )
    }
}

@Composable
fun DotsColumn(
    dotColor: Color
) {

    val infiniteTransition = rememberInfiniteTransition()

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 450),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .width(8.dp)
            .alpha(alpha),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Dot(dotColor)
        Spacer(modifier = Modifier.height(4.dp))
        Dot(dotColor)
    }
}