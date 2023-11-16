package ru.maslynem.presentation.songQuizSettingActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.domain.GetTopicListUseCase
import ru.maslynem.domain.topic.Topic

class SongQuizSettingsViewModel : ViewModel() {

    private val getTopicListUseCase = GetTopicListUseCase()

    private var _topicNumber = MutableLiveData<Int>()
    val topicNumber: LiveData<Int>
        get() = _topicNumber

    private var _cardNumber = MutableLiveData<Int>()
    val cardNumber: LiveData<Int>
        get() = _cardNumber

    private var _topicList = MutableLiveData<List<Topic>>()
    val topicList: LiveData<List<Topic>>
        get() = _topicList

    private var _shouldDisableRecycleView = MutableLiveData<Boolean>()
    val shouldDisableRecycleView: LiveData<Boolean>
        get() = _shouldDisableRecycleView

    private var _shouldEnableStartBtn = MutableLiveData<Boolean>()
    val shouldEnableStartBtn: LiveData<Boolean>
        get() = _shouldEnableStartBtn

    private var _countOfSelectedTopics = MutableLiveData<Int>()
    val countOfSelectedTopics: LiveData<Int>
        get() = _countOfSelectedTopics

    fun getTopicList() {
        _topicList.value = getTopicListUseCase.getTopicList()
        _countOfSelectedTopics.value = 0
    }

    fun onTopicItemClick(topic: Topic, isChecked: Boolean) {
//        val isLimit = _countOfSelectedTopics.value!! >= _topicNumber.value!!
//        _shouldShowLimitError.value = isChecked && isLimit
//        if (!isChecked || !isLimit) {
//            val find = _topicList.value?.find {
//                it.name == topic.name
//            }
//            find?.selected = isChecked
//            val value = _countOfSelectedTopics.value
//
//            _countOfSelectedTopics.value = if (isChecked) value?.plus(1)
//            else value?.minus(1)
//        }
//        _shouldDisableStartBtn.value = _countOfSelectedTopics.value!! != _topicNumber.value!!
//        return if (!isChecked || !isLimit) isChecked else false
        val find = _topicList.value?.find {
            it.name == topic.name
        }
        find?.selected = !topic.selected
        val value = _countOfSelectedTopics.value

        _countOfSelectedTopics.value = if (isChecked) value?.plus(1)
        else value?.minus(1)
        _shouldDisableRecycleView.value = _countOfSelectedTopics.value!! >= _topicNumber.value!!
        _shouldEnableStartBtn.value = _countOfSelectedTopics.value!! == _topicNumber.value!!
    }


    fun onTopicSpinnerItemSelectedClick(value: Int) {
        _topicNumber.value = value
        _shouldEnableStartBtn.value = _countOfSelectedTopics.value!! != _topicNumber.value!!
    }


}