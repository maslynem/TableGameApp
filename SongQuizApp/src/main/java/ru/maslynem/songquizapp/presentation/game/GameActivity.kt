package ru.maslynem.songquizapp.presentation.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {
    private val gameViewModel by viewModel<GameViewModel>()
    private lateinit var topicWithCardNumberListAdapter: TopicWithCardNumberListAdapter
    private lateinit var playerScoreListAdapter: PlayerScoreListAdapter
    private lateinit var binding: ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateIntent()
        if (savedInstanceState == null) {
            initializeViewModel()
        }
        setupRecyclerView()
        addObserversToViewModel()
        setupOnBackPressed()
    }

    private fun addObserversToViewModel() {
        gameViewModel.topicWithCardNumberList.observe(this) {
            topicWithCardNumberListAdapter.submitList(it)
        }

        gameViewModel.playerList.observe(this) {
            playerScoreListAdapter.playerList = it
        }
    }

    private fun setupRecyclerView() {
        topicWithCardNumberListAdapter = TopicWithCardNumberListAdapter()
        binding.rvTopicWithCard?.adapter = topicWithCardNumberListAdapter

        playerScoreListAdapter = PlayerScoreListAdapter()
        binding.rvPlayerScore?.adapter = playerScoreListAdapter

    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@GameActivity)
                    .setTitle(getString(R.string.back_pressed_title))
                    .setMessage(getString(R.string.back_pressed_message))
                    .setPositiveButton(getString(R.string.yes)) { _, _ -> finish() }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        })
    }

    private fun validateIntent() {
        if (!intent.hasExtra(EXTRA_CARD_NUMBER)) {
            throw RuntimeException("Param card number is absent")
        }
        if (!intent.hasExtra(EXTRA_TOPIC_LIST)) {
            throw RuntimeException("Param topic list is absent")
        }
    }

    private fun initializeViewModel() {
        val cardNumber = intent.getIntExtra(EXTRA_CARD_NUMBER, UNDEFINED_CARD_NUMBER)
        val topicList = intent.getStringArrayListExtra(EXTRA_TOPIC_LIST)!!
        gameViewModel.initializeGame(cardNumber, topicList)
    }

    companion object {
        private const val EXTRA_TOPIC_LIST = "extra_topic_list"
        private const val EXTRA_CARD_NUMBER = "extra_card_number"
        const val UNDEFINED_CARD_NUMBER = -1

        fun newIntent(context: Context, topicList: ArrayList<String>, cardNumber: Int): Intent {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(EXTRA_CARD_NUMBER, cardNumber)
            intent.putExtra(EXTRA_TOPIC_LIST, topicList)
            return intent
        }
    }
}