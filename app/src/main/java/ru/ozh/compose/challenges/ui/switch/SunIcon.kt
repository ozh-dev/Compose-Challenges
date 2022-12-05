package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val LightCount = 8
        val LightRotateAngle = 45f
        val sizePx = with(LocalDensity.current) { width.toPx() }
        val circleRadius = with(LocalDensity.current) { 4.dp.toPx() }
        val strokeWidth = with(LocalDensity.current) { 2.dp.toPx() }

        val circleAnimation = remember { Animatable(0f) }
        val lightsAlphaAnimation = remember { Array(LightCount) { Animatable(0f) } }
        val lightsOffsetAnimation = remember { Array(LightCount) { Animatable(0.75f) } }

        LaunchedEffect(Unit) {
            circleAnimation.animateTo(
                1f,
                animationSpec = TweenSpec(
                    durationMillis = 75
                )
            )

            lightsAlphaAnimation.zip(lightsOffsetAnimation) { alpha, offset -> alpha to offset }
                .withIndex()
                .forEach { (index, value) ->
                    val (alpha, offset) = value
                    delay(index * 5L)
                    launch {
                        alpha.animateTo(
                            1f,
                            animationSpec = TweenSpec(
                                durationMillis = 10
                            )
                        )
                    }
                    launch {
                        offset.animateTo(
                            1f,
                            animationSpec = TweenSpec(
                                durationMillis = 10
                            )
                        )
                    }
                }
        }

        Canvas(modifier = modifier) {

            drawCircle(
                color = color,
                radius = circleRadius * circleAnimation.value,
                style = Stroke(width = strokeWidth)
            )

            repeat(LightCount) { index ->
                rotate(degrees = -90 + (index * LightRotateAngle)) {
                    val lightStartX = sizePx * 0.8f
                    val lightEndX = sizePx * if (index % 2 == 0) 1f else 0.95f

                    val lightY = sizePx / 2

                    val lightStart = Offset(
                        x = lightStartX * lightsOffsetAnimation[index].value,
                        y = lightY
                    )
                    val lightEnd = Offset(
                        x = lightEndX * lightsOffsetAnimation[index].value,
                        y = lightY
                    )

                    drawLine(
                        color = color.copy(alpha = lightsAlphaAnimation[index].value),
                        start = lightStart,
                        end = lightEnd,
                        strokeWidth = strokeWidth * lightsOffsetAnimation[index].value,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 32, heightDp = 32, showBackground = false)
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
            color = Color.White,
        )
    }
}