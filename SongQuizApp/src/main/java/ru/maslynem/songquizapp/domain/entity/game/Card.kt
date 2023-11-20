package ru.maslynem.songquizapp.domain.entity.game

import ru.maslynem.songquizapp.domain.entity.topic.Topic

data class Card(
    val topic: Topic,
    val word: String
)
