package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.dropdown.Selectable
import com.cafecomandroid.myjournalvoice.core.utils.UiText
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.MoodChipContent
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceDaySection
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoicesFilter

data class VoicesState(
    val voices: Map<UiText, List<VoiceUI>> = emptyMap(),
    val hasVoicesRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilters: Boolean = false,
    val isLoading: Boolean = false,
    val moods: List<Selectable<MoodUI>> = emptyList(),
    val topics: List<Selectable<String>> = emptyList(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedVoiceFilterChip: VoicesFilter? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics),
) {
    val sectionsList = voices
        .toList()
        .map { (dateHeader, voices) ->
            VoiceDaySection(
                date = dateHeader,
                voices = voices
            )
        }
}
