package ru.maslynem.songquizapp.domain.cardUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.topic.Topic

interface GameRepository {
    fun getTopicWithCardNumberList(topicList: List<String>, cardNumber: Int): LiveData<List<Topic>>

    fun removeCard(card: Card)

    fun getCardByTopic(topic: Topic): Card

}