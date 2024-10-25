package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SwitchMoon() {
    var isChecked by remember { mutableStateOf(true) }
    val colors = SwitchDefaults.colors().copy(
        uncheckedBorderColor = Color.Transparent,
        checkedThumbColor = Color.Transparent,
        checkedTrackColor = Color(0XFF261439),
        uncheckedTrackColor = Color(0xFFFFECB3),
    )
    Switch(
        modifier = Modifier.semantics {
            contentDescription = "SwitchMoon"
        },
        checked = isChecked,
        onCheckedChange = { isChecked = it },
        colors = colors,
        thumbContent = { MoonThumb(isChecked) }
    )
}

@Composable
@Preview
fun SwitchMoonPreview() {
    SwitchMoon()
}

@Composable
fun MoonThumb(isChecked: Boolean) {
    val translateFactor by animateFloatAsState(
        targetValue = if (isChecked) 0.45f else 1f,
        label = "translateFactor",
    )
    Canvas(
        modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(40)),
    ) {
        val diameter = size.maxDimension
        val radius = diameter / 2
        drawCircle(
            color = Color(0XFFFCC008),
            radius = radius,
        )

        translate(left = -diameter * translateFactor) {
            drawCircle(
                color = Color(0XFF261439),
                radius = radius
            )
        }
    }
}

@Composable
@Preview
fun MoonThumbPreview() {
    val isChecked by remember { mutableStateOf(false) }
    MoonThumb(isChecked)
}