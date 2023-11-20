package ru.maslynem.songquizapp.domain.cardUseCase

import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.topic.Topic

class GetCardByTopicUseCase(private val repository: GameRepository) {
    fun getCardByTopic(topic: Topic): Card {
        return repository.getCardByTopic(topic)
    }
}