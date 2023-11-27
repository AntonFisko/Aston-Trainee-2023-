package com.example.musicplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.musicplayer.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.initializeMediaPlayer(this)

        viewModel.currentPosition.observe(this) { currentPosition ->
            binding.songSeekBar.max = viewModel.mediaPlayer?.duration ?: 0
            binding.songSeekBar.progress = currentPosition ?: 0
        }

        binding.playButton.setOnClickListener {
            if (viewModel.isPlaying) {
                viewModel.stopMusicService(this)
            } else {
                val resourceId = viewModel.getCurrentSongId()
                val mediaUri = Uri.parse("android.resource://${this.packageName}/$resourceId")
                viewModel.startMusicService(mediaUri, this)
            }
            updateButtonState()
            viewModel.changeIsPlaying()
        }

        binding.previousButton.setOnClickListener {
            viewModel.previousSong()
        }

        binding.nextButton.setOnClickListener {
            viewModel.nextSong()
        }

    }

    private fun updateButtonState() {
        val drawableRes = if (viewModel.isPlaying) {
            R.drawable.music_play_play_button_svgrepo_com
        } else {
            R.drawable.media_player_music_pause_svgrepo_com
        }
        binding.playButton.setImageResource(drawableRes)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releaseMediaPlayer()
    }
}