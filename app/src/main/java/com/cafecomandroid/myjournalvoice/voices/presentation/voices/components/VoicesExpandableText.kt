package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme

@Composable
fun VoicesExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    collapseMaxLine: Int = 3,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(false) }
    val showMoreText = stringResource(R.string.show_more)
    var lastCharIndex by remember { mutableIntStateOf(0) }
    val primaryColor = MaterialTheme.colorScheme.primary
    val textToShow = remember(text, isClickable, isExpanded) {
        buildAnnotatedString {
            when {
                isClickable && !isExpanded -> {
                    val adjustText = text
                        .substring(
                            startIndex = 0,
                            endIndex = lastCharIndex
                        )
                        .dropLast(showMoreText.length + 3)
                        .dropLastWhile {
                            Character.isWhitespace(it) || it == '.'
                        }

                    append(adjustText)
                    append("...")
                    withStyle(
                        style = SpanStyle(
                            color = primaryColor,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(showMoreText)
                    }
                }

                else -> append(text)
            }
        }
    }
    Text(
        text = textToShow,
        maxLines = if (isExpanded) Int.MAX_VALUE else collapseMaxLine,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable,
                interactionSource = null,
                indication = null
            ) {
                isExpanded = !isExpanded
            }
            .animateContentSize(),
        onTextLayout = { results ->
            if (!isExpanded && results.hasVisualOverflow) {
                isClickable = true
                lastCharIndex = results.getLineEnd(lineIndex = collapseMaxLine - 1)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun VoicesExpandableTextPreview() {
    MyJournalVoiceTheme {
        VoicesExpandableText(
            text = buildString { repeat(400) { append("Hello ") } }
        )
    }
}
