package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class VoicesViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(VoicesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = VoicesState()
        )

    fun onAction(action: VoicesAction) {
        when (action) {
            VoicesAction.OnFabClick -> {
                TODO()
            }

            VoicesAction.OnFabLongClick -> {
                TODO()
            }

            VoicesAction.OnMoodChipClick -> {
                TODO()
            }

            is VoicesAction.OnRemoveFilters -> {
                TODO()
            }

            VoicesAction.OnSettingsClick -> {
                TODO()
            }

            VoicesAction.OnTopicChipClick -> {
                TODO()
            }
        }
    }
}
