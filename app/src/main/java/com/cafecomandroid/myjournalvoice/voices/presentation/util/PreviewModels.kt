package com.cafecomandroid.myjournalvoice.voices.presentation.util

import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.models.PlaybackUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceUI
import java.time.Instant
import java.time.ZonedDateTime
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

internal object PreviewModels {
    val voiceUI = VoiceUI(
        id = 0,
        moodUI = MoodUI.PEACEFUL,
        title = "some audio",
        recordAt = Instant.now(),
        note = buildString { repeat(200) { append("hello ") } },
        topics = listOf("love"),
        amplitudes = (1..30).map { Random.nextFloat() },
        playbackTotalDuration = 250.seconds,
        playbackCurrentDuration = 50.seconds,
        playbackUI = PlaybackUI.PAUSED
    )

    val todayVoices = (1..5).map {
        voiceUI.copy(
            id = it,
            recordAt = Instant.now(),
            moodUI = MoodUI.SAD
        )
    }

    val yesterdayVoices = (1..5).map {
        voiceUI.copy(
            id = it,
            recordAt = ZonedDateTime.now().minusDays(1).toInstant(),
            moodUI = MoodUI.NEUTRAL
        )
    }

    val dayVoices = (1..5).map {
        voiceUI.copy(
            id = it,
            recordAt = ZonedDateTime.now().minusDays(3).toInstant()
        )
    }
}