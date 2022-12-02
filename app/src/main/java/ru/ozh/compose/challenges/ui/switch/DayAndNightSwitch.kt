package ru.ozh.compose.challenges.ui.switch

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.AnimationDuration
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.Black
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.Orange
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.RippleRadius
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.SwitchHeight
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.SwitchWidth
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.ThumbDiameter
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.ThumbPathLength
import ru.ozh.compose.challenges.ui.switch.SwitchConsts.ThumbStartOffset
import kotlin.math.roundToInt


@Composable
fun SwitchDaN(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val minBound = with(LocalDensity.current) { ThumbStartOffset.toPx() }
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scaleX = remember { Animatable(1f) }
    val scaleY = remember { Animatable(1f) }

    val scope = rememberCoroutineScope()

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                launch {
                    offset.animateTo(
                        targetValue = targetValue,
                        animationSpec = tween(
                            durationMillis = AnimationDuration,
                            easing = OvershootInterpolator(1.5f).toEasing()
                        )
                    )
                }

                launch {
                    scaleY.animateTo(
                        targetValue = 0.8f,
                        animationSpec = tween(
                            durationMillis = AnimationDuration / 2,
                        )
                    )

                    coroutineScope {
                        launch {
                            scaleX.animateTo(
                                targetValue = 0.9f,
                                animationSpec = tween(
                                    durationMillis = AnimationDuration / 4,
                                )
                            )

                            scaleX.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = AnimationDuration / 2,
                                )
                            )
                        }
                        launch {
                            scaleY.animateTo(
                                targetValue = 1.1f,
                                animationSpec = tween(
                                    durationMillis = AnimationDuration / 4,
                                )
                            )

                            scaleY.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = AnimationDuration / 2,
                                )
                            )
                        }
                    }
                }
            }
        }
        onDispose { }
    }

    val toggleableModifier = if (onCheckedChange != null) {
        Modifier.toggleable(
            value = checked,
            onValueChange = onCheckedChange,
            enabled = enabled,
            role = Role.Switch,
            interactionSource = interactionSource,
            indication = null
        )
    } else {
        Modifier
    }

    Box(
        modifier
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .requiredSize(SwitchWidth, SwitchHeight)
    ) {
        SwitchImpl(
            scaleX = scaleX.asState(),
            scaleY = scaleY.asState(),
            checked = checked,
            thumbValue = offset.asState(),
            interactionSource = interactionSource,
            thumbShape = RoundedCornerShape(50),
        )
    }
}

@Composable
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    thumbValue: State<Float>,
    scaleX: State<Float>,
    scaleY: State<Float>,
    interactionSource: InteractionSource,
    thumbShape: Shape,
) {
    val trackColor by animateColorAsState(if (checked) Orange else Black)

    val modifier = Modifier
        .align(Alignment.Center)
        .width(SwitchWidth)
        .height(SwitchHeight)
        .background(trackColor, RoundedCornerShape(50))

    Box(modifier) {

        SunIcon(
            modifier = Modifier
                .offset(x = 2.dp)
                .requiredSize(24.dp)
                .align(Alignment.CenterStart)
                .scale(0.9f),
            color = Color.White
        )

        MoonIcon(
            modifier = Modifier
                .offset(x = (-2).dp)
                .requiredSize(24.dp)
                .align(Alignment.CenterEnd)
                .scale(0.9f),
            color = Color.White
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .graphicsLayer {
                    this.scaleX = scaleX.value
                    this.scaleY = scaleY.value
                    this.transformOrigin = if (checked) {
                        TransformOrigin(3f, 0.5f)
                    } else {
                        TransformOrigin(0f, 0.5f)
                    }
                }
                .offset { IntOffset(thumbValue.value.roundToInt(), 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, RippleRadius)
                )
                .requiredSize(ThumbDiameter)
                .background(Color.White, thumbShape),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}

@Preview(
    widthDp = 300,
    heightDp = 300
)
@Composable
fun SwitchDaNPreview() {
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
    }
}