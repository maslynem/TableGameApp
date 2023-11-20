package ru.maslynem.songquizapp.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.databinding.ActivitySongQuizSettingsBinding
import ru.maslynem.songquizapp.presentation.game.GameActivity
import ru.maslynem.songquizapp.presentation.settings.topic.TopicCheckBox

class SettingsActivity : AppCompatActivity() {
    private lateinit var topicListAdapter: TopicListAdapter
    private lateinit var binding: ActivitySongQuizSettingsBinding
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongQuizSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSeekBarTime()

        setupRecyclerView()

        createTopicSpinner()
        createCardSpinner()

        addObserversToViewModel()

        binding.btnStartGame.setOnClickListener {
            val intent = GameActivity.newIntent(
                this,
                ArrayList(settingsViewModel.topicCheckBoxList.value!!.map(TopicCheckBox::name)),
                settingsViewModel.cardNumber.value!!
            )
            startActivity(intent)
        }

        settingsViewModel.getTopicList()
    }

    private fun setupSeekBarTime() {
        binding.tvTimeValue.text = binding.sbTime.progress.toString()
        binding.sbTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newProgress = progress / 10 * 10
                seekBar?.progress = newProgress
                binding.tvTimeValue.text = newProgress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun setupRecyclerView() {
        topicListAdapter = TopicListAdapter()
        binding.rvTopicList.adapter = topicListAdapter
        topicListAdapter.onTopicClick = { topic, isChecked ->
            settingsViewModel.onTopicItemClick(topic, isChecked)
        }
    }

    private fun createTopicSpinner() {
        val topicSpinner: Spinner = binding.spTopicNumber
        topicSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item: String = parent.getItemAtPosition(pos) as String
                settingsViewModel.onTopicSpinnerItemSelectedClick(item.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}

        }
        ArrayAdapter.createFromResource(
            this,
            R.array.topic_number_array,
            R.layout.spinner_item_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            topicSpinner.adapter = adapter
        }
    }

    private fun createCardSpinner() {
        val cardSpinner: Spinner = binding.spCardNumber
        cardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item: String = parent.getItemAtPosition(pos) as String
                settingsViewModel.onCardNumberSpinnerItemSelectedClick(item.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}

        }
        ArrayAdapter.createFromResource(
            this,
            R.array.card_number_array,
            R.layout.spinner_item_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cardSpinner.adapter = adapter
        }
    }

    private fun addObserversToViewModel() {
        /**
         * Слушатель topicNumber.
         * Когда пользователь в спиннере выбирает число, обновляется строка "Выбрано х из y тем"
         **/
        settingsViewModel.topicNumber.observe(this) {
            val string = getString(
                R.string.chosen_topics,
                settingsViewModel.countOfSelectedTopics.value,
                it
            )
            binding.tvChosenTopics.text = string
        }

        /**
         * Слушатель countOfChosenTopics.
         * Когда пользователь выбирает тему, обновляется строка "Выбрано х из y тем"
         **/
        settingsViewModel.countOfSelectedTopics.observe(this) {
            val string = getString(
                R.string.chosen_topics,
                it,
                settingsViewModel.topicNumber.value
            )
            binding.tvChosenTopics.text = string
        }

        /**
         * Слушатель topicList.
         * Передает измененный список тем в topicListAdapter
         * Такое происходит, когда пользователь выбирает тему. Состояние темы меняется в списке,
         * обновленный список передается в адаптер recycler view
         **/
        settingsViewModel.topicCheckBoxList.observe(this) {
            topicListAdapter.topicCheckBoxList = it
        }

        /**
         * Пока пользователь не выбрал нужное кол-во тем, кнопка заблокирована
         **/
        settingsViewModel.shouldEnableStartBtn.observe(this) {
            binding.btnStartGame.isEnabled = it
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}