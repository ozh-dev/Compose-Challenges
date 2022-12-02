package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring.StiffnessMediumLow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
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
                offset.animateTo(
                    targetValue = targetValue,
                    animationSpec = FloatSpringSpec(
                        dampingRatio = 0.6f,
                        stiffness = StiffnessMediumLow,
                    )
                )
            }

            scope.launch {
                scaleY.animateTo(0.75f)
                scaleY.animateTo(1f)

//                scaleX.animateTo(0.75f)
//                scaleX.animateTo(1f)
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
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .graphicsLayer {
                    this.scaleX = scaleX.value
                    this.scaleY = scaleY.value
                    this.transformOrigin = if (checked) {
                        TransformOrigin(2f, 0.5f)
                    } else {
                        TransformOrigin(1f, 0.5f)
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
/*            if (thumbContent != null) {
                val iconColor = colors.iconColor(enabled, checked)
                CompositionLocalProvider(
                    LocalContentColor provides iconColor.value,
                    content = thumbContent
                )
            }*/
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