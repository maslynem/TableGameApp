package ru.maslynem.songquizapp.presentation.game.cardActivity

import android.os.CountDownTimer
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


    fun initialize(topic: Topic, time: Int) {
        this.card = getCardByTopicUseCase.getCardByTopic(topic)

    }

    private fun initializeTimer(time: Int) {
        timer = object : CountDownTimer(time * 1000L, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
    }

    companion object {
        const val COUNT_DOWN_INTERVAL = 500L
    }
}