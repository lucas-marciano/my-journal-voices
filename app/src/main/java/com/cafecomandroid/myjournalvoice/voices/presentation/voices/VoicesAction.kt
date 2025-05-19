package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoicesFilter

sealed interface VoicesAction {
    data object OnMoodChipClick : VoicesAction
    data object OnTopicChipClick : VoicesAction
    data object OnFabClick : VoicesAction
    data object OnFabLongClick : VoicesAction
    data object OnSettingsClick : VoicesAction
    data class OnRemoveFilters(val filters: VoicesFilter) : VoicesAction
}
