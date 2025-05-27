package com.cafecomandroid.myjournalvoice.voices.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import kotlin.random.Random

@Composable
fun VoicePlayBar(
    amplitudeBarWidth: Dp,
    amplitudeBarSpacing: Dp,
    powerRations: List<Float>,
    mood: MoodUI,
    playerProgress: () -> Float,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val amplitudeBarWidthPx = amplitudeBarWidth.toPx()
        val amplitudeBarSpacingPx = amplitudeBarSpacing.toPx()
        val clipPath = Path()

        powerRations.forEachIndexed { index, ratio ->
            val height = ratio * size.height
            val xOffset = index * (amplitudeBarSpacingPx + amplitudeBarWidthPx)
            val yTopStart = center.y - height / 2f
            val topLeft = Offset(
                x = xOffset,
                y = yTopStart
            )
            val rectSize = Size(
                width = amplitudeBarWidthPx,
                height = height
            )
            val roundRect = RoundRect(
                rect = Rect(
                    offset = topLeft,
                    size = rectSize
                ),
                cornerRadius = CornerRadius(100f)
            )

            clipPath.addRoundRect(roundRect)

            drawRoundRect(
                color = mood.colorSet.desaturated,
                topLeft = topLeft,
                size = rectSize,
                cornerRadius = CornerRadius(100f)
            )
        }

        clipPath(clipPath) {
            drawRect(
                color = mood.colorSet.vivid,
                size = Size(
                    width = size.width * playerProgress(),
                    height = size.height
                )
            )
        }
    }
}

@Preview
@Composable
private fun VoicePlayBarPreview() {
    val ratios = remember {
        (1..15).map { Random.nextFloat() }
    }
    MyJournalVoiceTheme {
        VoicePlayBar(
            amplitudeBarWidth = 4.dp,
            amplitudeBarSpacing = 3.dp,
            powerRations = ratios,
            mood = MoodUI.SAD,
            playerProgress = { 0.11f },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}