package ru.maslynem.songquizapp.domain.game

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.topic.Topic

class GetTopicWithCardNumberUseCase(private val repository: GameRepository) {
    fun getTopicWithCardNumber(topicList: List<String>, cardNumber: Int): LiveData<List<Topic>> {
        return repository.getTopicWithCardNumberList(topicList, cardNumber)
    }
}