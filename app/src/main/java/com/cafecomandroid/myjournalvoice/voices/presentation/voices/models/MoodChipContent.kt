package com.cafecomandroid.myjournalvoice.voices.presentation.voices.models

import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.utils.UiText

data class MoodChipContent(
    val iconsRes: List<Int> = emptyList(),
    val title: UiText = UiText.StringResource(R.string.all_moods),
)
