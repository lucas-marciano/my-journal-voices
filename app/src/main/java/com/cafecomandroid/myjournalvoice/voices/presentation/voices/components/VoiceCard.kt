package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjournalvoice.core.presentation.ui.chips.HashtagChip
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.core.utils.defaultShadow
import com.cafecomandroid.myjournalvoice.voices.presentation.components.VoiceMoodPlayer
import com.cafecomandroid.myjournalvoice.voices.presentation.util.PreviewModels.voiceUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.TrackSizeInfo
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceUI

@Composable
fun VoiceCard(
    voiceUI: VoiceUI,
    onTrackSizeAvailable: (TrackSizeInfo) -> Unit,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .defaultShadow(
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = voiceUI.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = voiceUI.formattedRecordAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            VoiceMoodPlayer(
                moodUI = voiceUI.moodUI,
                playBackUI = voiceUI.playbackUI,
                playbackProgress = { voiceUI.playbackRatio },
                durationPlayed = voiceUI.playbackCurrentDuration,
                totalDuration = voiceUI.playbackTotalDuration,
                powerRations = voiceUI.amplitudes,
                onPlayClick = onPlay,
                onPauseClick = onPause,
                onTrackSizeAvailable = onTrackSizeAvailable
            )

            if (!voiceUI.note.isNullOrBlank()) {
                VoicesExpandableText(text = voiceUI.note)
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
            ) {
                voiceUI.topics.forEach {
                    HashtagChip(text = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun VoiceCardPreview() {
    MyJournalVoiceTheme {
        VoiceCard(
            voiceUI = voiceUI,
            onTrackSizeAvailable = {},
            onPlay = {},
            onPause = {}
        )
    }
}