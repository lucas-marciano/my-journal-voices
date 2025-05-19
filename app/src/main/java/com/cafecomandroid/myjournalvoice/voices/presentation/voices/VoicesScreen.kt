 package com.cafecomandroid.myjournalvoice.voices.presentation.voices import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

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

}

@Preview
@Composable
private fun Preview() {
    MyJornalVoiceTheme {
        VoicesScreen(
            state = VoicesState(),
            onAction = {}
        )
    }
}