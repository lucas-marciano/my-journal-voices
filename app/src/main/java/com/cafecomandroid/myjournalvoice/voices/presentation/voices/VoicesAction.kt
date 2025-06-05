package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.TrackSizeInfo
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoicesFilter

sealed interface VoicesAction {
    data object OnMoodChipClick : VoicesAction
    data object OnDismissMoodChipDropDown : VoicesAction
    data class OnFilterByMoodClick(val moodUI: MoodUI) : VoicesAction
    data object OnTopicChipClick : VoicesAction
    data object OnDismissTopicChipDropDown : VoicesAction
    data class OnFilterByTopicClick(val topic: String) : VoicesAction
    data object OnFabClick : VoicesAction
    data object OnFabLongClick : VoicesAction
    data object OnSettingsClick : VoicesAction
    data class OnRemoveFilters(val filters: VoicesFilter) : VoicesAction
    data class OnPlayVoiceClick(val idVoice: Int) : VoicesAction
    data class OnPauseVoiceClick(val idVoice: Int) : VoicesAction
    data class OnTrackSizeAvailable(val trackSize: TrackSizeInfo) : VoicesAction
}
