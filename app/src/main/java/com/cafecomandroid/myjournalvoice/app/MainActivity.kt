package com.cafecomandroid.myjournalvoice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cafecomandroid.myjournalvoice.core.presentation.ui.theme.MyJournalVoiceTheme
import com.cafecomandroid.myjournalvoice.voices.presentation.voices.VoicesRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyJournalVoiceTheme {
                VoicesRoot()
            }
        }
    }
}
