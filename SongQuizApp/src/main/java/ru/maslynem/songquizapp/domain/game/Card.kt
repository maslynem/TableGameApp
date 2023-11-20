package ru.maslynem.songquizapp.domain.game

import ru.maslynem.songquizapp.domain.topic.Topic

data class Card(
    val topic: Topic,
    val word: String
)
