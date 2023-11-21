package ru.maslynem.songquizapp.presentation.game.cardActivity

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.domain.cardUseCase.GetCardByTopicUseCase
import ru.maslynem.songquizapp.domain.cardUseCase.RemoveCardUseCase
import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import ru.maslynem.songquizapp.domain.playerUseCase.EditPlayerUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.GetPlayerListUseCase

class CardViewModel(
    private val getCardByTopicUseCase: GetCardByTopicUseCase,
    private val removeCardUseCase: RemoveCardUseCase,
    private val editPlayerUseCase: EditPlayerUseCase,
    private val getPlayerListUseCase: GetPlayerListUseCase
) : ViewModel() {

    private val _card: MutableLiveData<Card> = MutableLiveData()
    val card: LiveData<Card>
        get() = _card

    private lateinit var timer: CountDownTimer
    private val _timeInSec = MutableLiveData<Int>()
    val timeInSec: LiveData<Int>
        get() = _timeInSec

    private val _timeFinish = MutableLiveData<Unit>()
    val timeFinish: LiveData<Unit>
        get() = _timeFinish

    private val _shouldFinishActivity = MutableLiveData<Unit>()
    val shouldFinishActivity: LiveData<Unit>
        get() = _shouldFinishActivity

    private val winPlayerSet: MutableSet<Player> = mutableSetOf()

    fun initialize(topic: Topic, time: Int) {
        _card.value = getCardByTopicUseCase.getCardByTopic(topic)
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

    fun getPlayerList(): List<Player> {
        return getPlayerListUseCase.getPlayerList().value ?: emptyList()
    }

    fun addWinPlayer(player: Player) {
        winPlayerSet.add(player)
    }

    fun removeWinPlayer(player: Player) {
        winPlayerSet.remove(player)
    }

    fun addScoreToWinPlayer() {
        Log.d("CardActivity", "winPlayerSet $winPlayerSet")
        for (player: Player in winPlayerSet) {
            val copy = player.copy(score = player.score + 1)
            editPlayerUseCase.editPlayer(copy)
        }
        winPlayerSet.clear()
        removeCardUseCase.removeCard(_card.value!!)
        _shouldFinishActivity.value = Unit
    }

    fun startTimer() {
        timer.start()
    }

    fun stopTimer() {
        timer.cancel()
    }


    companion object {
        const val COUNT_DOWN_INTERVAL = 500L
    }
}