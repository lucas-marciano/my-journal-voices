package com.cafecomandroid.myjournalvoice.voices.presentation.voices.models

import com.cafecomandroid.myjournalvoice.core.utils.UiText

data class VoiceDaySection(
    val date: UiText,
    val voices: List<VoiceUI>
)
