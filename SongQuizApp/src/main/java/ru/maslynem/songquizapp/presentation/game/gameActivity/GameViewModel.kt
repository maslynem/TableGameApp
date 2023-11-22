package ru.maslynem.songquizapp.presentation.game.gameActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.domain.cardUseCase.GetTopicWithCardNumberUseCase
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import ru.maslynem.songquizapp.domain.playerUseCase.GetPlayerListUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.ResetPlayerScoreUseCase

class GameViewModel(
    private val getPlayerListUseCase: GetPlayerListUseCase,
    private val getTopicWithCardNumberUseCase: GetTopicWithCardNumberUseCase,
    private val resetPlayerScoreUseCase: ResetPlayerScoreUseCase
) : ViewModel() {

    lateinit var playerList: LiveData<List<Player>>
    lateinit var topicWithCardNumberList: LiveData<List<Topic>>

    fun initializeGame(cardNumber: Int, topicList: List<String>) {
        playerList = getPlayerListUseCase.getPlayerList()
        topicWithCardNumberList =
            getTopicWithCardNumberUseCase.getTopicWithCardNumber(topicList, cardNumber)
    }

    fun resetPlayerScore() {
        resetPlayerScoreUseCase.resetPlayerScore()
    }

    fun getPlayerList(): List<Player> {
        return playerList.value ?: emptyList()
    }
}