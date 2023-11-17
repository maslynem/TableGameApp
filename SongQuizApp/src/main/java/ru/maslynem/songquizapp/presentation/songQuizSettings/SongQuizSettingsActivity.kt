package ru.maslynem.songquizapp.presentation.songQuizSettings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.databinding.ActivitySongQuizSettingsBinding

class SongQuizSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongQuizSettingsBinding
    private lateinit var songQuizSettingsViewModel: SongQuizSettingsViewModel
    private lateinit var topicListAdapter: TopicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongQuizSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSeekBarTime()

        setupRecyclerView()

        createTopicSpinner()
        createCardSpinner()

        songQuizSettingsViewModel = ViewModelProvider(this)[SongQuizSettingsViewModel::class.java]

        addObserversToViewModel()

        songQuizSettingsViewModel.getTopicList()
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
            songQuizSettingsViewModel.onTopicItemClick(topic, isChecked)
        }
    }

    private fun createTopicSpinner() {
        val topicSpinner: Spinner = binding.spTopicNumber
        topicSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item: String = parent.getItemAtPosition(pos) as String
                songQuizSettingsViewModel.onTopicSpinnerItemSelectedClick(item.toInt())
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
        songQuizSettingsViewModel.topicNumber.observe(this) {
            val string = getString(
                R.string.chosen_topics,
                songQuizSettingsViewModel.countOfSelectedTopics.value,
                it
            )
            binding.tvChosenTopics.text = string
        }

        /**
         * Слушатель countOfChosenTopics.
         * Когда пользователь выбирает тему, обновляется строка "Выбрано х из y тем"
         **/
        songQuizSettingsViewModel.countOfSelectedTopics.observe(this) {
            val string = getString(
                R.string.chosen_topics,
                it,
                songQuizSettingsViewModel.topicNumber.value
            )
            binding.tvChosenTopics.text = string
        }

        /**
         * Слушатель topicList.
         * Передает измененный список тем в topicListAdapter
         * Такое происходит, когда пользователь выбирает тему. Состояние темы меняется в списке,
         * обновленный список передается в адаптер recycler view
         **/
        songQuizSettingsViewModel.topicCheckBoxList.observe(this) {
            topicListAdapter.topicCheckBoxList = it
        }

        /**
         * Пока пользователь не выбрал нужное кол-во тем, кнопка заблокирована
         **/
        songQuizSettingsViewModel.shouldEnableStartBtn.observe(this) {
            binding.btnStartGame.isEnabled = it
        }
    }

}