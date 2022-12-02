package ru.ozh.compose.challenges.ui.switch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SunIcon(
    modifier: Modifier,
    color: Color
) {

    BoxWithConstraints(modifier) {
        val width = this.maxWidth
        val height = this.maxHeight

        require(width == height) {
            error("width and height must be equals, but width = $width & height = $height")
        }

        val sizePx = with(LocalDensity.current) { width.toPx() }
        val circleRadius = with(LocalDensity.current) { 4.dp.toPx() }
        val strokeWidth = with(LocalDensity.current) { 2.dp.toPx() }

        val lightStartX = sizePx / 2
        val lightStartY = with(LocalDensity.current) { 5.dp.toPx() }
        val lightLongEndY = with(LocalDensity.current) { 0.dp.toPx() }
        val lightShortEndY = with(LocalDensity.current) { 2.dp.toPx() }

        Canvas(modifier = modifier) {

            drawCircle(
                color = color,
                radius = circleRadius,
                style = Stroke(width = strokeWidth)
            )

            repeat(8) { index ->
                rotate(degrees = index * 45f) {
                    val lightEndY = if (index % 2 == 0) lightLongEndY else lightShortEndY
                    val start = Offset(x = lightStartX, y = lightStartY)
                    val end = Offset(x = lightStartX, y = lightEndY)
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 32, heightDp = 32, showBackground = true)
@Composable
fun SunIconPreview() {
    Box(
        modifier = Modifier
            .requiredSize(24.dp)
            .background(color = Color.Blue)
    ) {
        SunIcon(
            modifier = Modifier
                .requiredSize(24.dp)
                .align(Alignment.CenterStart),
            color = Color.White
        )
    }
}