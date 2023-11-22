package ru.maslynem.songquizapp.presentation.game.cardActivity

import android.content.Context
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.databinding.ActivityCardBinding
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.entity.topic.Topic


class CardActivity : AppCompatActivity() {
    private val cardViewModel by viewModel<CardViewModel>()
    private lateinit var binding: ActivityCardBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateIntent()
        addObserversToViewModel()
        initializeViewModel()
        initializeMediaPlayer()
        setupBtnListener()
        setBlurOnCard()
        binding.tvCard.setBackgroundColor(intent.getIntExtra(EXTRA_COLOR, DEFAULT_COLOR))
    }


    private fun setupBtnListener() {
        binding.btnStartTimer.setOnClickListener {
            cardViewModel.startTimer()
            binding.btnStopTimer.isEnabled = true
            binding.btnGuessed.isEnabled = true
            binding.btnStartTimer.isEnabled = false
            delBlurFromCard()

        }

        binding.btnStopTimer.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            cardViewModel.stopTimer()
            binding.btnStartTimer.isEnabled = true
            binding.btnStopTimer.isEnabled = false
            setBlurOnCard()

        }
        binding.btnGuessed.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            cardViewModel.stopTimer()
            showWinPlayerDialog()
        }
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    private fun validateIntent() {
        if (!intent.hasExtra(EXTRA_TIME)) {
            throw RuntimeException("Param time is absent")
        }
        if (!intent.hasExtra(EXTRA_TOPIC)) {
            throw RuntimeException("Param topic is absent")
        }
    }

    private fun initializeViewModel() {
        val topic = intent.getParcelableExtra<Topic>(EXTRA_TOPIC)!!
        val time = intent.getIntExtra(EXTRA_TIME, DEFAULT_TIME)
        cardViewModel.initialize(topic, time)
    }

    private fun initializeMediaPlayer() {
        val defaultRingtoneUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(this, defaultRingtoneUri)
        val audioAttributes =
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .build()
        mediaPlayer.setAudioAttributes(audioAttributes)
        mediaPlayer.prepare()
    }

    private fun addObserversToViewModel() {
        cardViewModel.timeInSec.observe(this) {
            binding.tvTimerTime.text = it.toString()
        }

        cardViewModel.timeFinish.observe(this) {
            mediaPlayer.start()
        }

        cardViewModel.shouldFinishActivity.observe(this) {
            finish()
        }

        cardViewModel.card.observe(this) {
            binding.tvCard.text = it.word
        }
    }

    private fun showWinPlayerDialog() {
        val playerList = cardViewModel.getPlayerList()
        Log.d("Activity", "$playerList")
        if (playerList.isEmpty())
            return

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle(getString(R.string.winners))
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                cardViewModel.addScoreToWinPlayer()
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.cansel)) { dialog, which ->
                dialog.cancel()
            }
            .setMultiChoiceItems(
                playerList.map(Player::playerName).toTypedArray(), null
            ) { _, which, isChecked ->
                Log.d("Activity", "${playerList[which]}")
                if (isChecked) {
                    cardViewModel.addWinPlayer(player = playerList[which])
                } else {
                    cardViewModel.removeWinPlayer(player = playerList[which])
                }
            }
        val dialog: AlertDialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun setBlurOnCard() {
        binding.tvCard.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val radius = binding.tvCard.textSize / 3
        val filter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
        binding.tvCard.paint.maskFilter = filter
    }

    private fun delBlurFromCard() {
        binding.tvCard.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        binding.tvCard.paint.maskFilter = null
    }

    companion object {
        const val EXTRA_TIME = "extra_time"
        const val DEFAULT_TIME = 60
        const val EXTRA_TOPIC = "extra_topic"
        const val EXTRA_COLOR = "extra_color"
        const val DEFAULT_COLOR = Color.BLUE

        fun newIntent(context: Context, topic: Topic, color: Int, time: Int): Intent {
            val intent = Intent(context, CardActivity::class.java)
            intent.putExtra(EXTRA_TIME, time)
            intent.putExtra(EXTRA_TOPIC, topic)
            intent.putExtra(EXTRA_COLOR, color)
            return intent
        }
    }
}