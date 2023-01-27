@file:OptIn(ExperimentalAnimationApi::class)

package ru.ozh.compose.challenges.ui.clock

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaveClock(
    time: Time,
    plateSize: Dp = 40.dp,
    plateColor: Color = Color.White,
    plateElevation: Dp = 8.dp,
    plateRoundCorner: Dp = 3.dp,
    digitSize: TextUnit = 20.sp,
    digitColor: Color = Color.Black
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DigitPlate(
            value = time.hours,
            plateSize = plateSize,
            backgroundColor = plateColor,
            elevation = plateElevation,
            roundCorner = plateRoundCorner,
            textColor = digitColor,
            textSize = digitSize

        )
        DotsColumn()
        DigitPlate(
            value = time.minutes,
            plateSize = plateSize,
            backgroundColor = plateColor,
            elevation = plateElevation,
            roundCorner = plateRoundCorner,
            textColor = digitColor,
            textSize = digitSize
        )
        DotsColumn()
        DigitPlate(
            value = time.seconds,
            plateSize = plateSize,
            backgroundColor = plateColor,
            elevation = plateElevation,
            roundCorner = plateRoundCorner,
            textColor = digitColor,
            textSize = digitSize
        )
    }
}

@Composable
fun DigitPlate(
    value: Int,
    plateSize: Dp,
    roundCorner: Dp,
    backgroundColor: Color,
    elevation: Dp = 0.dp,
    textColor: Color,
    textSize: TextUnit
) {
    val backGroundShape = RoundedCornerShape(size = roundCorner)
    WiggleBox(
        modifier = Modifier
            .shadow(elevation = elevation, shape = backGroundShape)
            .size(plateSize)
            .clip(shape = backGroundShape)
            .background(color = backgroundColor),
        key = value,
        shakeOffset = plateSize / 10,
        shakeDuration = 300,
        shakeDelay = 0
    ) {
        DropDigitText(
            value = value,
            textColor = textColor,
            textSize = textSize
        )
    }
}

@Composable
fun DropDigitText(
    value: Int,
    textColor: Color,
    textSize: TextUnit
) {
    SlideContentAnimation(targetState = value) { targetCount ->
        Text(
            text = "%02d".format(targetCount),
            fontSize = textSize,
            color = textColor,
        )
    }
}

@Composable
fun WiggleBox(
    modifier: Modifier,
    key: Any,
    shakeOffset: Dp = 0.dp,
    shakeDuration: Int = 0,
    shakeDelay: Int = 0,
    content: @Composable BoxScope.() -> Unit
) {
    var targetOffset by remember { mutableStateOf(0.dp) }

    val animateOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = shakeDuration / 2,
                delayMillis = shakeDelay,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            targetOffset = 0.dp
        }
    )

    LaunchedEffect(key) {
        targetOffset = shakeOffset
    }

    //todo canvas + brush + color shadow
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(y = animateOffset)
            .then(modifier),
        content = content
    )
}

/*fun Modifier.shake(enabled: Boolean) = composed(

    factory = {

        val scale by animateFloatAsState(
            targetValue = if (enabled) .9f else 1f,
            animationSpec = repeatable(
                iterations = 5,
                animation = tween(durationMillis = 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)*/

@Composable
fun SlideContentAnimation(
    targetState: Int,
    content: @Composable AnimatedVisibilityScope.(targetState: Int) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height }
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height }
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        },
        content = content
    )
}