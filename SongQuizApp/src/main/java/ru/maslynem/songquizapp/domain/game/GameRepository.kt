package ru.maslynem.songquizapp.domain.game

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.topic.Topic

interface GameRepository {
    fun getTopicWithCardNumberList(topicList: List<String>, cardNumber: Int): LiveData<List<Topic>>

    fun removeCard(card: Card)

}