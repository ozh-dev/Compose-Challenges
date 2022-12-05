package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MoonIcon(
    modifier: Modifier,
    color: Color
) {

    BoxWithConstraints(modifier) {
        val width = this.maxWidth
        val height = this.maxHeight
        val sizePx = with(LocalDensity.current) { width.toPx() }

        require(width == height) {
            error("width and height must be equals, but width = $width & height = $height")
        }

        val scaleAnimation = remember { Animatable(0.8f) }
        val rotateAnimation = remember { Animatable(180f) }
        val moonStrokeWidth = with(LocalDensity.current) { 1.dp.toPx() }
        val path = Path()

        LaunchedEffect(Unit) {
            launch {
                rotateAnimation.animateTo(
                    0f,
                    animationSpec = TweenSpec(
                        durationMillis = 250
                    )
                )
            }

            launch {
                scaleAnimation.animateTo(
                    1f,
                    animationSpec = TweenSpec(
                        durationMillis = 250
                    )
                )
            }
        }

        Canvas(modifier = modifier) {
            path.reset()
            path.moveTo(x = sizePx * 0.7f, y = 0f)
            path.cubicTo(
                x1 = 0f,
                y1 = 0f,
                x2 = 0f,
                y2 = sizePx,
                x3 = sizePx * 0.7f,
                y3 = sizePx,
            )

            path.moveTo(x = sizePx * 0.7f, y = 0f)
            path.cubicTo(
                x1 = sizePx * 0.3f,
                y1 = sizePx * 0.2f,
                x2 = sizePx * 0.3f,
                y2 = sizePx * 0.8f,
                x3 = sizePx * 0.7f,
                y3 = sizePx,
            )

            scale(scale = scaleAnimation.value) {
                rotate(rotateAnimation.value - 15f) {
                    drawPath(path, color, style = Stroke(width = moonStrokeWidth))
                }
            }
        }
    }
}