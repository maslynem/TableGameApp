package ru.maslynem.songquizapp.presentation.songQuizSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.domain.topic.GetTopicListUseCase
import ru.maslynem.songquizapp.domain.topic.Topic
import ru.maslynem.songquizapp.presentation.songQuizSettings.topic.TopicCheckBox

class SettingsViewModel(private val getTopicListUseCase: GetTopicListUseCase) : ViewModel() {

    private var _topicNumber = MutableLiveData<Int>()
    val topicNumber: LiveData<Int>
        get() = _topicNumber

    private var _cardNumber = MutableLiveData<Int>()
    val cardNumber: LiveData<Int>
        get() = _cardNumber

    private var _topicCheckBoxList = MutableLiveData<List<TopicCheckBox>>()
    val topicCheckBoxList: LiveData<List<TopicCheckBox>>
        get() = _topicCheckBoxList

    private var _shouldEnableStartBtn = MutableLiveData<Boolean>()
    val shouldEnableStartBtn: LiveData<Boolean>
        get() = _shouldEnableStartBtn

    private var _countOfSelectedTopics = MutableLiveData<Int>()
    val countOfSelectedTopics: LiveData<Int>
        get() = _countOfSelectedTopics

    private var isTopicListEnabled: Boolean = true

    fun getTopicList() {
        _topicCheckBoxList.value = getTopicListUseCase.getTopicList()
            .value?.map { topic: Topic ->
                TopicCheckBox(
                    topic.name,
                    selected = false,
                    enabled = true
                )
            }
        _countOfSelectedTopics.value = 0
    }

    fun onTopicItemClick(topicCheckBox: TopicCheckBox, isChecked: Boolean) {
        val find = _topicCheckBoxList.value?.find {
            it.name == topicCheckBox.name
        }
        find?.selected = !topicCheckBox.selected

        updateCountOfSelectedTopics(isChecked)

        _shouldEnableStartBtn.value = _countOfSelectedTopics.value!! == _topicNumber.value!!

        checkEnableStateOfTopicList()
    }

    private fun checkEnableStateOfTopicList() {
        val isLimit = _countOfSelectedTopics.value!! >= _topicNumber.value!!
        if (isLimit && isTopicListEnabled) {
            changeEnableStateOfTopicList(false)
            isTopicListEnabled = false
        } else if (!isLimit && !isTopicListEnabled) {
            changeEnableStateOfTopicList(true)
            isTopicListEnabled = true
        }
    }

    private fun changeEnableStateOfTopicList(enabled: Boolean) {
        _topicCheckBoxList.value?.forEach {
            if (!it.selected) {
                it.enabled = enabled
            }
        }
        _topicCheckBoxList.value = _topicCheckBoxList.value
    }

    private fun updateCountOfSelectedTopics(isChecked: Boolean) {
        val value = _countOfSelectedTopics.value
        _countOfSelectedTopics.value = if (isChecked) value?.plus(1)
        else value?.minus(1)
    }

    fun onTopicSpinnerItemSelectedClick(value: Int) {
        _topicNumber.value = value
        _shouldEnableStartBtn.value = _countOfSelectedTopics.value!! == _topicNumber.value!!
        checkEnableStateOfTopicList()
    }


}