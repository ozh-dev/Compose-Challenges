package ru.ozh.compose.challenges.ui.grid

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    @DrawableRes res: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().clip(CircleShape),
            painter = painterResource(id = res),
            contentDescription = ""
        )
    }
}

@Composable
fun CrossLine() {
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
        onDraw = {
            drawLine(
                start = Offset(this.size.width / 2, 0f),
                end = Offset(this.size.width / 2, this.size.height),
                color = Color.White,
                strokeWidth = 4f
            )

            drawLine(
                start = Offset(0f, this.size.height / 2),
                end = Offset(this.size.width, this.size.height / 2),
                color = Color.White,
                strokeWidth = 4f
            )
        }
    )
}