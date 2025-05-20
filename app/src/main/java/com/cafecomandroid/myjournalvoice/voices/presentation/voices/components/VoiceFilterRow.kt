package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjornalvoice.R
import com.cafecomandroid.myjournalvoice.core.presentation.ui.chips.MultiChoiceChip
import com.cafecomandroid.myjournalvoice.core.presentation.ui.dropdown.Selectable
import com.cafecomandroid.myjournalvoice.core.presentation.ui.dropdown.SelectableDropDownOptionsMenu
import com.cafecomandroid.myjournalvoice.core.utils.UiText
import com.cafecomandroid.myjournalvoice.voices.presentation.models.MoodUI
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.VoicesAction
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.MoodChipContent
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.models.VoicesFilter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VoiceFilterRow(
    moodChipContent: MoodChipContent,
    hasActiveMoodFilter: Boolean,
    selectedVoiceFilterChip: VoicesFilter?,
    moods: List<Selectable<MoodUI>>,
    topicChipTitle: UiText,
    hasActiveTopicFilter: Boolean,
    topics: List<Selectable<String>>,
    onAction: (VoicesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var dropDownOffset by remember { mutableStateOf(IntOffset.Zero) }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val dropDownMaxHeight = (screenHeight * 0.3f).dp

    FlowRow(
        modifier = modifier
            .padding(16.dp)
            .onGloballyPositioned {
                dropDownOffset = IntOffset(
                    x = 0,
                    y = it.size.height
                )
            },
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Moods

        MultiChoiceChip(displayText = moodChipContent.title.asString(),
            onClick = {
                onAction(VoicesAction.OnMoodChipClick)
            },
            leadingContent = {
                if (moodChipContent.iconsRes.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(end = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(-4.dp)
                    ) {
                        moodChipContent.iconsRes.forEach { iconRes ->
                            Image(
                                modifier = Modifier.size(16.dp),
                                imageVector = ImageVector.vectorResource(iconRes),
                                contentDescription = moodChipContent.title.asString()
                            )
                        }
                    }
                }
            },
            isClearVisible = hasActiveMoodFilter,
            isDropDownVisible = selectedVoiceFilterChip == VoicesFilter.MOODS,
            isHighlighted = hasActiveMoodFilter || selectedVoiceFilterChip == VoicesFilter.MOODS,
            onClearButtonClick = {
                onAction(VoicesAction.OnRemoveFilters(VoicesFilter.MOODS))
            },
            dropDownMenu = {
                SelectableDropDownOptionsMenu(
                    dropDownOffset = dropDownOffset,
                    maxDropDownHeight = dropDownMaxHeight,
                    leadingIcon = { item ->
                        Image(
                            imageVector = ImageVector.vectorResource(item.iconSet.fill),
                            contentDescription = item.title.asString()
                        )
                    },
                    items = moods,
                    itemDisplayText = { moodUi ->
                        moodUi.title.asString(context)
                    },
                    onDismiss = {
                        onAction(VoicesAction.OnDismissMoodChipDropDown)
                    },
                    key = { moodUi -> moodUi.title.asString(context) },
                    onItemClick = { moodUi ->
                        onAction(VoicesAction.OnFilterByMoodClick(moodUi.item))
                    })
            })

        // Topics

        MultiChoiceChip(displayText = topicChipTitle.asString(),
            onClick = {
                onAction(VoicesAction.OnTopicChipClick)
            },
            isClearVisible = hasActiveTopicFilter,
            isDropDownVisible = selectedVoiceFilterChip == VoicesFilter.TOPICS,
            isHighlighted = hasActiveTopicFilter || selectedVoiceFilterChip == VoicesFilter.TOPICS,
            onClearButtonClick = {
                onAction(VoicesAction.OnRemoveFilters(VoicesFilter.TOPICS))
            },
            dropDownMenu = {
                if (topics.isEmpty()) {
                    SelectableDropDownOptionsMenu(
                        items = listOf(
                            Selectable(
                                item = stringResource(R.string.message_no_topics_saved),
                                selected = false
                            )
                        ),
                        itemDisplayText = { it },
                        onDismiss = {
                            onAction(VoicesAction.OnDismissTopicChipDropDown)
                        },
                        key = { it },
                        onItemClick = {},
                        dropDownOffset = dropDownOffset,
                        maxDropDownHeight = dropDownMaxHeight
                    )
                } else {
                    SelectableDropDownOptionsMenu(dropDownOffset = dropDownOffset,
                        maxDropDownHeight = dropDownMaxHeight,
                        leadingIcon = {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.hashtag),
                                contentDescription = null
                            )
                        },
                        items = topics,
                        itemDisplayText = { topics ->
                            topics
                        },
                        onDismiss = {
                            onAction(VoicesAction.OnDismissTopicChipDropDown)
                        },
                        key = { topics -> topics },
                        onItemClick = { topic ->
                            onAction(VoicesAction.OnFilterByTopicClick(topic.item))
                        }
                    )
                }
            }
        )
    }
}
