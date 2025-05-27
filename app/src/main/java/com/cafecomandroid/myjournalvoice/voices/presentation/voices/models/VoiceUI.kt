package com.cafecomandroid.myjournalvoice.voices.presentation.voices.models

import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.models.PlaybackUI
import com.cafecomandroid.myjournalvoice.voices.presentation.util.toReadableTime
import java.time.Instant
import kotlin.time.Duration

data class VoiceUI(
    val id: Int,
    val moodUI: MoodUI,
    val title: String,
    val recordAt: Instant,
    val note: String?,
    val topics: List<String>,
    val amplitudes: List<Float>,
    val playbackTotalDuration: Duration,
    val playbackCurrentDuration: Duration = Duration.ZERO,
    val playbackUI: PlaybackUI = PlaybackUI.STOPPED
) {
    val formattedRecordAt = recordAt.toReadableTime()
    val playbackRatio = (playbackCurrentDuration / playbackTotalDuration).toFloat()
}
