package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun SwitchDaN(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    //colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val thumbPaddingStart = SwitchStartOffset
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() }
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scope = rememberCoroutineScope()

    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                offset.animateTo(targetValue, AnimationSpec)
            }
        }
        onDispose { }
    }

    // TODO: Add Swipeable modifier b/223797571
    val toggleableModifier =
        if (onCheckedChange != null) {
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
            checked = checked,
            enabled = enabled,
            thumbValue = offset.asState(),
            interactionSource = interactionSource,
            thumbShape = RoundedCornerShape(50),
            minBound = thumbPaddingStart,
            maxBound = ThumbPathLength,
            thumbContent = thumbContent,
        )
    }
}

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    thumbValue: State<Float>,
    thumbContent: (@Composable () -> Unit)?,
    interactionSource: InteractionSource,
    thumbShape: Shape,
    minBound: Dp,
    maxBound: Dp,
) {
    val trackColor by animateColorAsState(if (checked) orange else black)
    val isPressed by interactionSource.collectIsPressedAsState()

//    val thumbValueDp = with(LocalDensity.current) { thumbValue.value.toDp() }

    val thumbSizeDp = ThumbDiameter

    val thumbOffset = if (isPressed) {
        with(LocalDensity.current) {
            if (checked) {
                ThumbPathLength
            } else {
                SwitchStartOffset
            }.toPx()
        }
    } else {
        thumbValue.value
    }

    val trackShape = RoundedCornerShape(50)
    val modifier = Modifier
        .align(Alignment.Center)
        .width(SwitchWidth)
        .height(SwitchHeight)
        .background(trackColor, trackShape)

    Box(modifier) {
        val thumbColor = Color.White
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset { IntOffset(thumbOffset.roundToInt(), 0) }
               /* .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, StateLayerSize / 2)
                )*/
                .requiredSize(thumbSizeDp)
                .background(thumbColor, thumbShape),
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

internal val PressedHandleWidth = 28.0.dp
internal val ThumbDiameter = 24.0.dp
internal val TrackOutlineWidth = 2.0.dp
internal val StateLayerSize = 40.0.dp
private val SwitchStartOffset = 4.dp
private val SwitchWidth = 60.0.dp
private val SwitchHeight = 32.0.dp
private val ThumbPadding = (SwitchHeight - ThumbDiameter) / 2
private val ThumbPathLength = (SwitchWidth - ThumbDiameter) - ThumbPadding

private val black = Color(red = 53, green = 53, blue = 53)
private val orange = Color(red = 243, green = 109, blue = 24)
private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

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