package com.cafecomandroid.myjournalvoice.voices.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.Pause
import com.cafecomandroid.myjournalvoice.core.utils.defaultShadow
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.models.PlaybackUI

@Composable
fun VoicePlaybackButton(
    playbackUiState: PlaybackUI,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    moodUI: MoodUI,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = moodUI.colorSet.desaturated,
            contentColor = moodUI.colorSet.vivid,
        ),
        onClick = when (playbackUiState) {
            PlaybackUI.PLAYING -> onPauseClick
            PlaybackUI.PAUSED,
            PlaybackUI.STOPPED -> onPlayClick
        },
        modifier = modifier.defaultShadow()
    ) {
        Icon(
            imageVector = when (playbackUiState) {
                PlaybackUI.PLAYING -> Icons.Filled.Pause
                PlaybackUI.PAUSED,
                PlaybackUI.STOPPED -> Icons.Filled.PlayArrow
            },
            contentDescription = when (playbackUiState) {
                PlaybackUI.PLAYING -> stringResource(R.string.playing)
                PlaybackUI.PAUSED -> stringResource(R.string.paused)
                PlaybackUI.STOPPED -> stringResource(R.string.stopped)
            }

        )
    }
}

@Preview
@Composable
private fun VoicePlaybackButtonPreview() {
    MyJournalVoiceTheme {
        VoicePlaybackButton(
            playbackUiState = PlaybackUI.PAUSED,
            onPlayClick = {},
            onPauseClick = {},
            moodUI = MoodUI.SAD,
        )
    }
}
