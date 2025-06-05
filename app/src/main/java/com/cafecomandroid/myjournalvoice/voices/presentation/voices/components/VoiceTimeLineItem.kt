package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.voices.presentation.util.PreviewModels.voiceUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.RelativePosition
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.TrackSizeInfo
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoiceUI

@Composable
fun VoiceTimeLineItem(
    voiceUI: VoiceUI,
    relativePosition: RelativePosition,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onTrackSize: (TrackSizeInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (relativePosition != RelativePosition.SINGLE_ENTRY) {
                VerticalDivider(
                    modifier = when (relativePosition) {
                        RelativePosition.FIRST -> noLineTopIcon
                        RelativePosition.LAST -> noLineBottomIcon
                        RelativePosition.BETWEEN -> Modifier
                        else -> Modifier
                    }
                )
            }

            Image(
                imageVector = ImageVector.vectorResource(voiceUI.moodUI.iconSet.fill),
                contentDescription = voiceUI.moodUI.title.asString(),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        VoiceCard(
            voiceUI = voiceUI,
            onTrackSizeAvailable = onTrackSize,
            onPlay = onPlayClick,
            onPause = onPauseClick,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

private val noLineTopIcon = Modifier.padding(top = 16.dp)
private val noLineBottomIcon = Modifier.height(8.dp)

@Preview
@Composable
private fun VoiceTimeLineItemPreview() {

    MyJournalVoiceTheme {
        VoiceTimeLineItem(
            voiceUI = voiceUI,
            relativePosition = RelativePosition.BETWEEN,
            onPauseClick = {},
            onPlayClick = {},
            onTrackSize = {}
        )
    }
}