package ru.maslynem.songquizapp.domain.cardUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.topic.Topic

class GetTopicWithCardNumberUseCase(private val repository: GameRepository) {
    fun getTopicWithCardNumber(topicList: List<String>, cardNumber: Int): LiveData<List<Topic>> {
        return repository.getTopicWithCardNumberList(topicList, cardNumber)
    }
}