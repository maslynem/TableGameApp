package ru.maslynem.songquizapp.domain.game

class RemoveCardUseCase(private val repository: GameRepository) {
    fun removeCard(card: Card) {
        repository.removeCard(card)
    }
}