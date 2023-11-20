package ru.maslynem.songquizapp.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.domain.game.Card
import ru.maslynem.songquizapp.domain.game.GetTopicWithCardNumberUseCase
import ru.maslynem.songquizapp.domain.game.RemoveCardUseCase
import ru.maslynem.songquizapp.domain.player.GetPlayerListUseCase
import ru.maslynem.songquizapp.domain.player.Player
import ru.maslynem.songquizapp.domain.topic.Topic

class GameViewModel(
    private val getPlayerListUseCase: GetPlayerListUseCase,
    private val getTopicWithCardNumberUseCase: GetTopicWithCardNumberUseCase,
    private val removeCardUseCase: RemoveCardUseCase
) : ViewModel() {

    lateinit var playerList: LiveData<List<Player>>
    lateinit var topicWithCardNumberList: LiveData<List<Topic>>

    fun initializeGame(cardNumber: Int, topicList: List<String>) {
        playerList = getPlayerListUseCase.getPlayerList()
        topicWithCardNumberList =
            getTopicWithCardNumberUseCase.getTopicWithCardNumber(topicList, cardNumber)
    }

    fun removeCard(card: Card) {
        removeCardUseCase.removeCard(card)
    }

}