package com.cafecomandroid.myjournalvoice.voices.presentation.voices

data class VoicesState(
    val hasVoicesRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilters: Boolean = false,
    val isLoading: Boolean = false,
)
