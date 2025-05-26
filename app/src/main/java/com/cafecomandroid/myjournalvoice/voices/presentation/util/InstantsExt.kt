package com.cafecomandroid.myjournalvoice.voices.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.toReadableTime(): String {
    val formated = DateTimeFormatter.ofPattern("HH:mm")
    return this.atZone(ZoneId.systemDefault()).format(formated)
}