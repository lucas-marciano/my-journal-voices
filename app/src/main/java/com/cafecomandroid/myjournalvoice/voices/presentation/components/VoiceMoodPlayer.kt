package com.cafecomandroid.myjournalvoice.voices.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.core.utils.formatMMSS
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.models.PlaybackUI
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun VoiceMoodPlayer(
    moodUI: MoodUI,
    playBackUI: PlaybackUI,
    playbackProgress: () -> Float,
    durationPlayed: Duration,
    totalDuration: Duration,
    powerRations: List<Float>,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    amplitudeBarWidth: Dp = 5.dp,
    amplitudeBarSpacing: Dp = 4.dp,
    onTrackSizeAvailable: () -> Float = { 0f }
) {
    val formatedText = remember(durationPlayed, totalDuration) {
        "${durationPlayed.formatMMSS()} / ${totalDuration.formatMMSS()} "
    }

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = moodUI.colorSet.faded
    )
    {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VoicePlaybackButton(
                onPauseClick = onPauseClick,
                onPlayClick = onPlayClick,
                moodUI = moodUI,
                playbackUiState = playBackUI
            )

            VoicePlayBar(
                amplitudeBarWidth = amplitudeBarWidth,
                amplitudeBarSpacing = amplitudeBarSpacing,
                powerRations = powerRations,
                mood = moodUI,
                playerProgress = playbackProgress,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        vertical = 10.dp,
                        horizontal = 8.dp
                    )
                    .fillMaxHeight()
            )

            Text(
                text = formatedText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun VoiceMoodPlayerPreview() {
    val ratios = remember {
        (1..15).map { Random.nextFloat() }
    }
    MyJournalVoiceTheme {
        VoiceMoodPlayer(
            moodUI = MoodUI.PEACEFUL,
            playBackUI = PlaybackUI.PAUSED,
            playbackProgress = { 0.04f },
            powerRations = ratios,
            onPauseClick = {},
            onPlayClick = {},
            totalDuration = 250.seconds,
            durationPlayed = 50.seconds
        )
    }
}
