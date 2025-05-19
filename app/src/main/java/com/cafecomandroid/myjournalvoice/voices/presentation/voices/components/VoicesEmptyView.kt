package com.cafecomandroid.myjournalvoice.voices.presentation.voices.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.cafecomandroid.myjornalvoice.R

@Composable
fun VoicesEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.moods),
            contentDescription = stringResource(R.string.message_moods_empty)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.message_moods_empty),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.message_moods_empty_sub),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
