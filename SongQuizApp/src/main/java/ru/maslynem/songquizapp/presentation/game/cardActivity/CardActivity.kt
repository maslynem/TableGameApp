package ru.maslynem.songquizapp.presentation.game.cardActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maslynem.songquizapp.databinding.ActivityCardBinding
import ru.maslynem.songquizapp.domain.entity.topic.Topic

class CardActivity : AppCompatActivity() {
    private val cardViewModel by viewModel<CardViewModel>()
    private lateinit var binding: ActivityCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateIntent()
        addObserversToViewModel()
        initializeViewModel()
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

    private fun addObserversToViewModel() {
        cardViewModel.timeInSec.observe(this) {
            binding.tvTimerTime.text = it.toString()
        }
    }

    companion object {
        const val EXTRA_TIME = "extra_time"
        const val DEFAULT_TIME = 60
        const val EXTRA_TOPIC = "extra_topic"

        fun newIntent(context: Context, topic: Topic, time: Int): Intent {
            val intent = Intent(context, CardActivity::class.java)
            intent.putExtra(EXTRA_TIME, time)
            intent.putExtra(EXTRA_TOPIC, topic)
            return intent
        }
    }
}