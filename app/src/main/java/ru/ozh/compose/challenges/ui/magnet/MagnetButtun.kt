package ru.ozh.compose.challenges.ui.magnet

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun MagnetButtonScreen() {
    Surface {
        MagnetButtonBox(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
        )
    }
}

@Preview
@Composable
fun MagnetButtonScreenPreview() {
    MagnetButtonScreen()
}

@Composable
fun MagnetButtonBox(
    modifier: Modifier = Modifier,
    buttonSize: Dp = 72.dp,
) {
    BoxWithConstraints(modifier) {

        val boxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val boxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val buttonHalfSizePx = with(LocalDensity.current) { buttonSize.toPx() }

        val bounds = Rect(
            topLeft = Offset.Zero,
            bottomRight = Offset(boxWidth - buttonHalfSizePx, boxHeight - buttonHalfSizePx)
        )

        var offsetX by remember { mutableStateOf(0) }
        var offsetY by remember { mutableStateOf(0) }
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX, offsetY) }
                .size(buttonSize)
                .background(color = Color.Blue, shape = RoundedCornerShape(20.dp))
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val x = offsetX + dragAmount.x.toInt()
                        val y = offsetY + dragAmount.y.toInt()
                        if(bounds.contains(Offset(x.toFloat(), y.toFloat()))) {
                            offsetX = x
                            offsetY = y
                        }
                    }
                }
        )
    }
}
