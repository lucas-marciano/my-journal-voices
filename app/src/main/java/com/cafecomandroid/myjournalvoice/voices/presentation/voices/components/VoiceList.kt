package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.core.utils.UiText
import com.cafecomandroid.myjournalvoice.voices.presentation.util.PreviewModels.dayVoices
import com.cafecomandroid.myjournalvoice.voices.presentation.util.PreviewModels.todayVoices
import com.cafecomandroid.myjournalvoice.voices.presentation.util.PreviewModels.yesterdayVoices
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.RelativePosition
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.TrackSizeInfo
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceDaySection

@Composable
fun VoiceList(
    sections: List<VoiceDaySection>,
    onPlayClick: (echoId: Int) -> Unit,
    onPauseClick: (echoId: Int) -> Unit,
    onTrackSizeAvailable: (TrackSizeInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        sections.forEachIndexed { sectionIndex, (dateHeader, voices) ->
            stickyHeader {
                if (sectionIndex > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(
                    text = dateHeader.asString().uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            itemsIndexed(
                items = voices,
                key = { _, voice -> voice.id }
            ) { i, voice ->
                VoiceTimeLineItem(
                    voiceUI = voice,
                    relativePosition = calculateRelativePosition(i, voices.size),
                    onPlayClick = { onPlayClick(voice.id) },
                    onPauseClick = { onPauseClick(voice.id) },
                    onTrackSize = onTrackSizeAvailable
                )
            }
        }
    }
}

private fun calculateRelativePosition(index: Int, size: Int): RelativePosition {
    return when {
        size == 1 -> RelativePosition.SINGLE_ENTRY
        index == 0 && size > 1 -> RelativePosition.FIRST
        index == (size - 1) -> RelativePosition.LAST
        else -> RelativePosition.BETWEEN
    }
}

@Preview(showBackground = true)
@Composable
private fun VoiceListPreview() {

    val sections = listOf(
        VoiceDaySection(
            date = UiText.Dynamic(stringResource(R.string.today)),
            voices = todayVoices
        ),
        VoiceDaySection(
            date = UiText.Dynamic(stringResource(R.string.yesterday)),
            yesterdayVoices
        ),
        VoiceDaySection(
            date = UiText.Dynamic("22/03/2025"),
            voices = dayVoices
        ),
    )

    MyJournalVoiceTheme {
        VoiceList(
            sections = sections,
            onPlayClick = { },
            onPauseClick = { },
            onTrackSizeAvailable = { },
        )
    }
}
