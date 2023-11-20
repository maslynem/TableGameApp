package ru.maslynem.songquizapp.domain.cardUseCase

import ru.maslynem.songquizapp.domain.entity.game.Card

class RemoveCardUseCase(private val repository: GameRepository) {
    fun removeCard(card: Card) {
        repository.removeCard(card)
    }
}