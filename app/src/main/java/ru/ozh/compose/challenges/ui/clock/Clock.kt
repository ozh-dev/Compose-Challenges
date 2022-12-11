package ru.ozh.compose.challenges.ui.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaveClock(time: Time) {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DigitPlate(value = time.hours)
        DotsColumn()
        DigitPlate(value = time.minutes)
        DotsColumn()
        DigitPlate(value = time.seconds)
    }
}

@Composable
fun DigitPlate(value: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = "%02d".format(value),
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}