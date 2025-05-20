package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.dropdown.Selectable
import com.cafecomandroid.myjournalvoice.core.utils.UiText
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.MoodChipContent
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoicesFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class VoicesViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val selectedMoodFilters = MutableStateFlow<List<MoodUI>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<String>>(emptyList())

    private val _state = MutableStateFlow(VoicesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observerFilters()
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

            }

            VoicesAction.OnFabLongClick -> {

            }

            VoicesAction.OnMoodChipClick -> {
                _state.update {
                    it.copy(
                        selectedVoiceFilterChip = VoicesFilter.MOODS
                    )
                }
            }

            VoicesAction.OnTopicChipClick -> {
                _state.update {
                    it.copy(
                        selectedVoiceFilterChip = VoicesFilter.TOPICS
                    )
                }
            }

            is VoicesAction.OnRemoveFilters -> {
                when (action.filters) {
                    VoicesFilter.MOODS -> selectedMoodFilters.update { emptyList() }
                    VoicesFilter.TOPICS -> selectedTopicFilters.update { emptyList() }
                }
            }

            VoicesAction.OnSettingsClick -> {

            }

            VoicesAction.OnDismissTopicChipDropDown,
            VoicesAction.OnDismissMoodChipDropDown -> {
                _state.update {
                    it.copy(
                        selectedVoiceFilterChip = null,
                    )
                }
            }

            is VoicesAction.OnFilterByMoodClick -> {
                toggleMoodFilters(action.moodUI)
            }

            is VoicesAction.OnFilterByTopicClick -> {
                toggleTopicFilters(action.topic)

            }
        }
    }

    private fun toggleMoodFilters(moodUI: MoodUI) {
        selectedMoodFilters.update {
            if (moodUI in it) {
                it - moodUI
            } else {
                it + moodUI
            }
        }
    }

    private fun toggleTopicFilters(topic: String) {
        selectedTopicFilters.update {
            if (topic in it) {
                it - topic
            } else {
                it + topic
            }
        }
    }

    private fun observerFilters() {
        combine(
            selectedTopicFilters,
            selectedMoodFilters
        ) { selectedTopic, selectedMood ->
            _state.update { localState ->
                localState.copy(
                    topics = localState.topics.map { selectableTopic ->
                        Selectable(
                            selectableTopic.item,
                            selectedTopic.contains(selectableTopic.item)
                        )
                    },
                    moods = MoodUI.entries.map { mood ->
                        Selectable(
                            mood,
                            selectedMood.contains(mood)
                        )
                    },
                    hasActiveMoodFilters = selectedMood.isNotEmpty(),
                    hasActiveTopicFilters = selectedTopic.isNotEmpty(),
                    topicChipTitle = selectedTopic.deriveTopicsToText(),
                    moodChipContent = selectedMood.asMoodChipContent()
                )
            }

        }.launchIn(viewModelScope)
    }

    private fun List<String>.deriveTopicsToText(): UiText {
        return when (this.size) {
            0 -> UiText.StringResource(R.string.all_topics)
            1 -> UiText.Dynamic(this.first())
            2 -> UiText.Dynamic("${first()}, ${last()}")
            else -> {
                val extraElements = size - 2
                UiText.Dynamic("${first()}, ${this[1]} +$extraElements")
            }
        }
    }

    private fun List<MoodUI>.asMoodChipContent(): MoodChipContent {
        if (this.isEmpty()) {
            return MoodChipContent()
        }
        val icons = this.map { it.iconSet.fill }
        val moodNames = this.map { it.title }

        return when (size) {
            1 -> MoodChipContent(
                iconsRes = icons,
                title = moodNames.first()
            )

            2 -> MoodChipContent(
                iconsRes = icons,
                title = UiText.Combined(
                    format = "%s, %s",
                    uiTexts = moodNames.toTypedArray()
                )
            )

            else -> {
                val extraElements = size - 2
                MoodChipContent(
                    iconsRes = icons,
                    title = UiText.Combined(
                        format = "%s, %s +$extraElements",
                        uiTexts = moodNames.take(2).toTypedArray()
                    )
                )
            }
        }
    }
}
