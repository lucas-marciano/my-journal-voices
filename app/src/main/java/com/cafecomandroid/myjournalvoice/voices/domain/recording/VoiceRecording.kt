package com.cafecomandroid.myjournalvoice.voices.domain.recording

import kotlinx.coroutines.flow.StateFlow

interface VoiceRecording {
    val recordingDetails: StateFlow<RecordingDetails>
    fun startRecord()
    fun pauseRecord()
    fun stopRecord()
    fun resumeRecord()
    fun cancelRecord()
}
