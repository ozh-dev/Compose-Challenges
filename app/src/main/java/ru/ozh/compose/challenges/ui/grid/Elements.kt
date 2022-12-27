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
fun IconRounded(
    modifier: Modifier = Modifier,
    @DrawableRes res: Int
) {
    Box(
        modifier = Modifier.clip(CircleShape).then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = res),
            contentDescription = ""
        )
    }
}

@Composable
fun CrossLine(
    color: Color = Color.White
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
        onDraw = {
            drawLine(
                start = Offset(this.size.width / 2, 0f),
                end = Offset(this.size.width / 2, this.size.height),
                color = color,
                strokeWidth = 4f
            )

            drawLine(
                start = Offset(0f, this.size.height / 2),
                end = Offset(this.size.width, this.size.height / 2),
                color = color,
                strokeWidth = 4f
            )
        }
    )
}