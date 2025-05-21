package com.cafecomandroid.myjournalvoice.voices.presentation.voices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.bgGradient
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.components.VoiceFilterRow
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.components.VoicesEmptyView
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.components.VoicesFloatingButton
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.components.VoicesTopBar

@Composable
fun VoicesRoot(
    viewModel: VoicesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    VoicesScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun VoicesScreen(
    state: VoicesState,
    onAction: (VoicesAction) -> Unit,
) {
    Scaffold(
        topBar = {
            VoicesTopBar(
                onSettingsClick = {
                    onAction(VoicesAction.OnSettingsClick)
                }
            )
        },
        floatingActionButton = {
            VoicesFloatingButton(
                onClick = {
                    onAction(VoicesAction.OnFabClick)
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = MaterialTheme.colorScheme.bgGradient
                )
                .padding(paddingValue)
        ) {
            VoiceFilterRow(
                moodChipContent = state.moodChipContent,
                hasActiveMoodFilter = state.hasActiveMoodFilters,
                selectedVoiceFilterChip = state.selectedVoiceFilterChip,
                moods = state.moods,
                topics = state.topics,
                topicChipTitle = state.topicChipTitle,
                hasActiveTopicFilter = state.hasActiveTopicFilters,
                onAction = { onAction(it) }
            )

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .wrapContentSize(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                !state.hasVoicesRecorded -> {
                    VoicesEmptyView(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .wrapContentSize(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyJournalVoiceTheme {
        VoicesScreen(
            state = VoicesState(
                isLoading = false,
            ),
            onAction = {}
        )
    }
}
