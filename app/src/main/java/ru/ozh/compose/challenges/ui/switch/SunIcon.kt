package ru.ozh.compose.challenges.ui.switch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
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
        val circleRadius = sizePx / 8
        val circleStrokeWidth = with(LocalDensity.current) { 2.dp.toPx() }
        val lightStart = with(LocalDensity.current) { 5.dp.toPx() }
        val lightLongEnd = with(LocalDensity.current) { 0.dp.toPx() }
        val lightShortEnd = with(LocalDensity.current) { 3.dp.toPx() }
        val lightStrokeWidth = with(LocalDensity.current) { 2.dp.toPx() }

        Canvas(modifier = modifier) {

            drawCircle(
                color = color,
                radius = circleRadius,
                style = Stroke(width = circleStrokeWidth)
            )

            repeat(8) { index ->
                rotate(degrees = index * 45f) {
                    val lightEnd = if (index % 2 == 0) lightLongEnd else lightShortEnd
                    val start = Offset(x = sizePx / 2, y = lightStart)
                    val end = Offset(x = sizePx / 2, y = lightEnd)
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = lightStrokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}
