package ru.maslynem.songquizapp.domain.playerUseCase

class ResetPlayerScoreUseCase(private val playerListRepository: PlayerListRepository) {
    fun resetPlayerScore() {
        playerListRepository.resetPlayerScore()
    }
}