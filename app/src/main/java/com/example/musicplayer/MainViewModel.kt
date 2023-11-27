package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer

class MainViewModel : ViewModel() {
    var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false

    private val songResourceIds: IntArray = getSongResourceIds()
    private var currentSongIndex: Int = 0

    val currentPosition = MutableLiveData<Int>()

    private var timer: Timer? = null

    fun changeIsPlaying() {
        isPlaying = !isPlaying
    }

    fun initializeMediaPlayer(activity: MainActivity) {
        mediaPlayer = MediaPlayer.create(activity, R.raw.song)

        mediaPlayer?.setOnCompletionListener {
            stopUpdatingSeekBar()
        }
    }

    fun startMusicService(mediaUri: Uri, context: Context) {
        val playIntent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_PLAY
            putExtra(MusicService.EXTRA_MEDIA_URI, mediaUri.toString())
        }
        context.startService(playIntent)
    }

    fun stopMusicService(context: Context) {
        val stopIntent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_STOP
        }
        context.startService(stopIntent)
    }

    private fun stopUpdatingSeekBar() {
        timer?.cancel()
        timer = null
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        stopUpdatingSeekBar()
    }

    private fun getSongResourceIds(): IntArray {
        return intArrayOf(R.raw.song, R.raw.song2)
    }

    fun getCurrentSongId(): Int {
        return songResourceIds[currentSongIndex]
    }

    fun previousSong() {
        currentSongIndex = (currentSongIndex - 1 + songResourceIds.size) % songResourceIds.size

    }

    fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songResourceIds.size

    }
}