package ru.maslynem.songquizapp.presentation.game.cardActivity

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.domain.cardUseCase.GetCardByTopicUseCase
import ru.maslynem.songquizapp.domain.cardUseCase.RemoveCardUseCase
import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import ru.maslynem.songquizapp.domain.playerUseCase.EditPlayerUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.GetPlayerListUseCase

class CardViewModel(
    private val getCardByTopicUseCase: GetCardByTopicUseCase,
    private val removeCardUseCase: RemoveCardUseCase,
    private val editPlayerUseCase: EditPlayerUseCase,
    private val getPlayerListUseCase: GetPlayerListUseCase
) : ViewModel() {

    private lateinit var card: Card
    private lateinit var timer: CountDownTimer
    private var _timeInSec = MutableLiveData<Int>()
    val timeInSec: LiveData<Int>
        get() = _timeInSec

    private var _timeFinish = MutableLiveData<Unit>()
    val timeFinish: LiveData<Unit>
        get() = _timeFinish

    fun initialize(topic: Topic, time: Int) {
        this.card = getCardByTopicUseCase.getCardByTopic(topic)
        initializeTimer(time)
    }

    private fun initializeTimer(time: Int) {
        _timeInSec.value = time
        timer = object : CountDownTimer(time * 1000L, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                _timeInSec.value = (millisUntilFinished / 1_000).toInt()
            }

            override fun onFinish() {
                _timeFinish.value = Unit
                timer.cancel()
            }
        }
    }

    fun startTimer() {
        timer.start()
    }

    companion object {
        const val COUNT_DOWN_INTERVAL = 500L
    }
}