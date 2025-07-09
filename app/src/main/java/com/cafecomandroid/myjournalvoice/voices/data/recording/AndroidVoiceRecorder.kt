package com.cafecomandroid.myjournalvoice.voices.data.recording

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import com.cafecomandroid.myjournalvoice.voices.domain.recording.RecordingDetails
import com.cafecomandroid.myjournalvoice.voices.domain.recording.VoiceRecording
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class AndroidVoiceRecorder(
    private val context: Context,
    private val applicationScope: CoroutineScope
) : VoiceRecording {

    private val _recordingDetails = MutableStateFlow(RecordingDetails())
    override val recordingDetails = _recordingDetails.asStateFlow()

    private val singleThreadDispatcher = Dispatchers.Default.limitedParallelism(1)

    private var recorder: MediaRecorder? = null
        get() {
            return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }
        }

    private var isRecording: Boolean = false
    private var isPaused: Boolean = false
    private var amplitudes = mutableListOf<Float>()

    private val tempFile: File
        get() {
            val uniqueId = UUID.randomUUID().toString()
            return File(
                context.cacheDir,
                "${TEMP_FILE_PREFIX}_$uniqueId.mp4"
            )
        }

    private var durationJob: Job? = null
    private var amplitudesJob: Job? = null


    override fun startRecord() {
        if (isRecording) return
        runCatching {
            resetSession()
            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128 * 1000)
                setAudioSamplingRate(44100)
                setOutputFile(tempFile.absolutePath)
                prepare()
                start()
            }
            isRecording = true
            isPaused = false

            startTrackingDuration()
            startTrackingAmplitudes()
        }.onFailure {
            errorRecord("startRecord: $it")
        }
    }

    override fun pauseRecord() {
        if (!isRecording || isPaused) return
        runCatching {
            recorder?.pause()

            durationJob?.cancel()
            amplitudesJob?.cancel()
        }.onFailure {
            errorRecord("pauseRecord: $it")
        }
    }

    override fun stopRecord() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            errorRecord("stopRecord: $e")
        } finally {
            _recordingDetails.update {
                it.copy(
                    amplitudes = amplitudes.toList(),
                    filePath = tempFile.absolutePath
                )
            }
            cleaningUp()
        }
    }

    override fun resumeRecord() {
        if (!isRecording || !isPaused) return

        runCatching {
            recorder?.resume()
            isPaused = false
            startTrackingDuration()
            startTrackingAmplitudes()
        }.onFailure {
            errorRecord("resumeRecord: $it")
        }
    }

    override fun cancelRecord() {
        startRecord()
        resetSession()
    }

    private fun startTrackingDuration() {
        durationJob = applicationScope.launch {
            var lasTime = System.currentTimeMillis()
            while (isRecording && !isPaused) {
                delay(10L)
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lasTime
                _recordingDetails.update {
                    it.copy(
                        duration = it.duration + elapsedTime.milliseconds
                    )
                }
                lasTime = System.currentTimeMillis()
            }
        }
    }

    private fun startTrackingAmplitudes() {
        amplitudesJob = applicationScope.launch {
            while (isRecording) {
                val amplitude = getAmplitude()
                withContext(singleThreadDispatcher) {
                    amplitudes.add(amplitude)
                }
                delay(100L)
            }
        }
    }

    private fun getAmplitude(): Float {
        return if (isRecording) {
            try {
                val maxAmplitude = recorder?.maxAmplitude
                val amplitudeRatio = maxAmplitude?.takeIf { it > 0f }?.run {
                    (this / MAX_AMPLITUDE.toFloat()).coerceIn(0f, 1f)
                }
                amplitudeRatio ?: 0f
            } catch (e: Exception) {
                errorRecord("getAmplitude: $e")
                0f
            }
        } else {
            0f
        }
    }

    private fun resetSession() {
        _recordingDetails.update { RecordingDetails() }
        applicationScope.launch(singleThreadDispatcher) {
            amplitudes.clear()
            cleaningUp()
        }
    }

    private fun cleaningUp() {
        Log.d(AndroidVoiceRecorder::class.java.simpleName, "cleaning up")
        recorder = null
        isRecording = false
        isPaused = false
        durationJob?.cancel()
        amplitudesJob?.cancel()
    }

    private fun errorRecord(message: String) {
        Log.e(AndroidVoiceRecorder::class.java.simpleName, message)
        recorder?.release()
        recorder = null
    }

    companion object {
        private const val TEMP_FILE_PREFIX = "temp_recording"
        private const val MAX_AMPLITUDE = 26_000L
    }
}
