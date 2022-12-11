package ru.ozh.compose.challenges.ui.clock

import java.util.*

data class Time(val hours: Int, val minutes: Int, val seconds: Int)

val calendar = Calendar.getInstance()

fun currentTime(): Time {
    return Time(
        hours = calendar.get(Calendar.HOUR_OF_DAY),
        minutes = calendar.get(Calendar.MINUTE),
        seconds = calendar.get(Calendar.SECOND),
    )
}